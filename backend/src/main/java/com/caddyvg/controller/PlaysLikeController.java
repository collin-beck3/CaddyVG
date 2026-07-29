package com.caddyvg.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.caddyvg.dto.PlaysLikeResponse;
import com.caddyvg.service.PlaysLikeService;

@RestController
@RequestMapping("/api/playslike")
public class PlaysLikeController {

    private final PlaysLikeService playsLikeService;

    public PlaysLikeController(PlaysLikeService playsLikeService) {
        this.playsLikeService = playsLikeService;
    }

    @GetMapping
    public PlaysLikeResponse getPlaysLike(
            @RequestParam Double userLatitude,
            @RequestParam Double userLongitude,
            @RequestParam Double targetLatitude,
            @RequestParam Double targetLongitude
    ) {
        return playsLikeService.calculate(
                userLatitude,
                userLongitude,
                targetLatitude,
                targetLongitude
        );
    }
}
