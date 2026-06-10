package com.caddyvg.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.caddyvg.dto.CourseImportRequest;
import com.caddyvg.model.Course;
import com.caddyvg.model.Hazard;
import com.caddyvg.model.Hole;
import com.caddyvg.repository.CourseRepository;
import com.caddyvg.repository.HazardRepository;
import com.caddyvg.repository.HoleRepository;

@Service
public class CourseImportService {

    private final CourseRepository courseRepository;
    private final HoleRepository holeRepository;
    private final HazardRepository hazardRepository;
    private final OpenStreetMapService openStreetMapService;

    public CourseImportService(
            CourseRepository courseRepository,
            HoleRepository holeRepository,
            HazardRepository hazardRepository,
            OpenStreetMapService openStreetMapService
    ) {
        this.courseRepository = courseRepository;
        this.holeRepository = holeRepository;
        this.hazardRepository = hazardRepository;
        this.openStreetMapService = openStreetMapService;
    }

    public Course importCourse(CourseImportRequest request) {

        Course course = courseRepository.findByExternalCourseId(request.getExternalCourseId())
                .orElseGet(() -> {
                    Course newCourse = new Course();

                    newCourse.setExternalCourseId(request.getExternalCourseId());
                    newCourse.setName(request.getName());
                    newCourse.setLatitude(request.getLatitude());
                    newCourse.setLongitude(request.getLongitude());

                    return courseRepository.save(newCourse);
                });

        List<Hole> existingHoles = holeRepository.findByCourseCourseId(course.getCourseId());

        if (!existingHoles.isEmpty()) {
            return course;
        }

        Map features = openStreetMapService.getGolfFeaturesNearCourse(
                request.getLatitude(),
                request.getLongitude()
        );

        List<Map<String, Object>> elements =
                (List<Map<String, Object>>) features.get("elements");

        if (elements == null) {
            return course;
        }

        List<FeaturePoint> tees = collectFeaturePoints(elements, "tee");
        List<FeaturePoint> greens = collectFeaturePoints(elements, "green");

        List<Hole> importedHoles = importHoles(course, elements, tees, greens);

        importHazards(importedHoles, elements);

        return course;
    }

    private List<Hole> importHoles(
            Course course,
            List<Map<String, Object>> elements,
            List<FeaturePoint> tees,
            List<FeaturePoint> greens
    ) {

        List<Hole> holes = new ArrayList<>();
        List<Integer> importedHoleNumbers = new ArrayList<>();

        for (Map<String, Object> element : elements) {
            Map<String, Object> tags = (Map<String, Object>) element.get("tags");

            if (tags == null) {
                continue;
            }

            if (!"hole".equals(String.valueOf(tags.get("golf")))) {
                continue;
            }

            Integer holeNumber = parseInteger(tags.get("ref"));

            if (holeNumber == null) {
                continue;
            }

            if (importedHoleNumbers.contains(holeNumber)) {
                continue;
            }

            importedHoleNumbers.add(holeNumber);

            Double fallbackLatitude = getElementLatitude(element);
            Double fallbackLongitude = getElementLongitude(element);

            if (fallbackLatitude == null || fallbackLongitude == null) {
                continue;
            }

            FeaturePoint teePoint = findExactFeaturePoint(tees, holeNumber);

            FeaturePoint greenPoint = findExactFeaturePoint(greens, holeNumber);

            if (greenPoint == null) {
                greenPoint = findNearestFeaturePoint(
                        greens,
                        fallbackLatitude,
                        fallbackLongitude
                );
            }

            Double teeLatitude = teePoint != null ? teePoint.latitude() : fallbackLatitude;
            Double teeLongitude = teePoint != null ? teePoint.longitude() : fallbackLongitude;

            Double greenLatitude = greenPoint != null ? greenPoint.latitude() : fallbackLatitude;
            Double greenLongitude = greenPoint != null ? greenPoint.longitude() : fallbackLongitude;

            Hole hole = new Hole();

            hole.setCourse(course);
            hole.setHoleNumber(holeNumber);
            hole.setPar(parseIntegerOrDefault(tags.get("par"), 0));
            hole.setHandicap(parseIntegerOrDefault(tags.get("handicap"), 0));

            hole.setTeeLatitude(teeLatitude);
            hole.setTeeLongitude(teeLongitude);

            hole.setFrontGreenLatitude(greenLatitude);
            hole.setFrontGreenLongitude(greenLongitude);

            hole.setMiddleGreenLatitude(greenLatitude);
            hole.setMiddleGreenLongitude(greenLongitude);

            hole.setBackGreenLatitude(greenLatitude);
            hole.setBackGreenLongitude(greenLongitude);

            holes.add(holeRepository.save(hole));
        }

        holes.sort(Comparator.comparingInt(Hole::getHoleNumber));

        return holes;
    }

    private List<FeaturePoint> collectFeaturePoints(
            List<Map<String, Object>> elements,
            String golfType
    ) {

        List<FeaturePoint> points = new ArrayList<>();

        for (Map<String, Object> element : elements) {
            Map<String, Object> tags = (Map<String, Object>) element.get("tags");

            if (tags == null) {
                continue;
            }

            if (!golfType.equals(String.valueOf(tags.get("golf")))) {
                continue;
            }

            Double latitude = getElementLatitude(element);
            Double longitude = getElementLongitude(element);

            if (latitude == null || longitude == null) {
                continue;
            }

            Integer ref = parseInteger(tags.get("ref"));

            points.add(new FeaturePoint(ref, latitude, longitude));
        }

        return points;
    }

    private FeaturePoint findExactFeaturePoint(
            List<FeaturePoint> points,
            Integer holeNumber
    ) {

        for (FeaturePoint point : points) {
            if (point.ref() != null && point.ref().equals(holeNumber)) {
                return point;
            }
        }

        return null;
    }

    private FeaturePoint findNearestFeaturePoint(
            List<FeaturePoint> points,
            Double latitude,
            Double longitude
    ) {

        if (points.isEmpty()) {
            return null;
        }

        FeaturePoint nearest = points.get(0);

        double nearestDistance = distanceSquared(
                latitude,
                longitude,
                nearest.latitude(),
                nearest.longitude()
        );

        for (FeaturePoint point : points) {
            double distance = distanceSquared(
                    latitude,
                    longitude,
                    point.latitude(),
                    point.longitude()
            );

            if (distance < nearestDistance) {
                nearest = point;
                nearestDistance = distance;
            }
        }

        return nearest;
    }

    private void importHazards(List<Hole> holes, List<Map<String, Object>> elements) {

        if (holes.isEmpty()) {
            return;
        }

        for (Map<String, Object> element : elements) {
            Map<String, Object> tags = (Map<String, Object>) element.get("tags");

            if (tags == null) {
                continue;
            }

            String golfTag = String.valueOf(tags.get("golf"));
            String naturalTag = String.valueOf(tags.get("natural"));

            boolean isBunker = "bunker".equals(golfTag);
            boolean isWater = "water".equals(naturalTag);

            if (!isBunker && !isWater) {
                continue;
            }

            Double latitude = getElementLatitude(element);
            Double longitude = getElementLongitude(element);

            if (latitude == null || longitude == null) {
                continue;
            }

            Hole nearestHole = findNearestHole(holes, latitude, longitude);

            Hazard hazard = new Hazard();
            hazard.setHole(nearestHole);
            hazard.setLatitude(latitude);
            hazard.setLongitude(longitude);

            if (isBunker) {
                hazard.setType("BUNKER");
                hazard.setLabel("Bunker");
            } else {
                hazard.setType("WATER");
                hazard.setLabel("Water");
            }

            hazardRepository.save(hazard);
        }
    }

    private Hole findNearestHole(List<Hole> holes, Double latitude, Double longitude) {

        Hole nearest = holes.get(0);

        double nearestDistance = distanceSquared(
                latitude,
                longitude,
                nearest.getMiddleGreenLatitude(),
                nearest.getMiddleGreenLongitude()
        );

        for (Hole hole : holes) {
            double distance = distanceSquared(
                    latitude,
                    longitude,
                    hole.getMiddleGreenLatitude(),
                    hole.getMiddleGreenLongitude()
            );

            if (distance < nearestDistance) {
                nearest = hole;
                nearestDistance = distance;
            }
        }

        return nearest;
    }

    private double distanceSquared(Double lat1, Double lon1, Double lat2, Double lon2) {
        double deltaLat = lat1 - lat2;
        double deltaLon = lon1 - lon2;

        return deltaLat * deltaLat + deltaLon * deltaLon;
    }

    private Double getElementLatitude(Map<String, Object> element) {

        if (element.get("lat") != null) {
            return ((Number) element.get("lat")).doubleValue();
        }

        if (element.get("center") instanceof Map center) {
            return ((Number) center.get("lat")).doubleValue();
        }

        return null;
    }

    private Double getElementLongitude(Map<String, Object> element) {

        if (element.get("lon") != null) {
            return ((Number) element.get("lon")).doubleValue();
        }

        if (element.get("center") instanceof Map center) {
            return ((Number) center.get("lon")).doubleValue();
        }

        return null;
    }

    private Integer parseInteger(Object value) {

        if (value == null) {
            return null;
        }

        try {
            String cleaned = String.valueOf(value).replaceAll("[^0-9]", "");

            if (cleaned.isBlank()) {
                return null;
            }

            return Integer.parseInt(cleaned);
        } catch (Exception e) {
            return null;
        }
    }

    private int parseIntegerOrDefault(Object value, int defaultValue) {

        Integer parsed = parseInteger(value);

        if (parsed == null) {
            return defaultValue;
        }

        return parsed;
    }

    private record FeaturePoint(
            Integer ref,
            Double latitude,
            Double longitude
    ) {
    }
}