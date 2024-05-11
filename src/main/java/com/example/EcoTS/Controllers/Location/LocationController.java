package com.example.EcoTS.Controllers.Location;

import com.example.EcoTS.Models.Locations;
import com.example.EcoTS.Services.Location.LocationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin
@Tag(name = "Location APIs", description = "Apis for get location and show it to front-end")
public class LocationController {
    @Autowired
    private LocationService locationService;
    @GetMapping("/locations/byType")
    public List<Locations> getLocationsByType(@RequestParam("type") String type) {
        return locationService.getLocationsByType(type);
    }
    @GetMapping("/locations")
    public List<Locations> getAllLocations() {
        return locationService.getAllLocations();
    }
}
