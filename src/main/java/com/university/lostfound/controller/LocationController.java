package com.university.lostfound.controller;

import com.university.lostfound.model.Location;
import com.university.lostfound.model.User;
import com.university.lostfound.service.LocationService;
import com.university.lostfound.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/locations")
@CrossOrigin(origins = "*")
public class LocationController {

    private final LocationService locationService;
    private final UserService userService;

    public LocationController(LocationService locationService, UserService userService) {
        this.locationService = locationService;
        this.userService = userService;
    }

    @GetMapping
    public List<Location> getAll() {
        return locationService.getAllLocations();
    }

    @PostMapping
    public Location create(@RequestBody Location location) {
        return locationService.saveLocation(location);
    }

    @PostMapping("/seed")
    public ResponseEntity<String> seed() {
        locationService.seedRwandaLocations();
        return ResponseEntity.ok("Rwanda locations seeded successfully (if not already present).");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getById(@PathVariable UUID id) {
        return locationService.getLocationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<Location> getByCode(@PathVariable String code) {
        return locationService.getLocationByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/province/{code}/users")
    public List<User> getUsersByProvince(@PathVariable String code) {
        return userService.getUsersByProvinceCode(code);
    }

    @GetMapping("/province-name/{name}/users")
    public List<User> getUsersByProvinceName(@PathVariable String name) {
        return userService.getUsersByProvinceName(name);
    }
}
