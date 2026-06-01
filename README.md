#  CaddyVG

## Overview
CaddyVG is a virtual golf caddie that provides golfers with
plays-like yardages by combining GPS distance, elevation
changes, wind conditions, and temperature adjustments.

## Features
- GPS hole navigation
- Front, middle, and back green distances
- Elevation-adjusted yardages
- Wind-adjusted yardages
- Temperature-adjusted yardages
- Hazard visualization
- Digital scorecard

## Tech Stack

Frontend
- SwiftUI
- MapKit
- CoreLocation

Backend
- Spring Boot REST API
  or
- Node.js/Express

Database
- PostgreSQL

External APIs
- OpenStreetMap
- Open-Meteo
- Open Elevation

## Architecture

iOS App
    ↓
REST API
    ↓
Database

         ↓
Weather API
Elevation API
Course Data API

Controller = receives API requests
Service = contains business logic
Repository = talks to the database
Model = database objects
DTO = request/response objects