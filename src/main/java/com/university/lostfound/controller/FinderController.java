package com.university.lostfound.controller;

import com.university.lostfound.model.Finder;
import com.university.lostfound.service.FinderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/finders")
public class FinderController {

    private final FinderService finderService;

    public FinderController(FinderService finderService) {
        this.finderService = finderService;
    }

    @GetMapping
    public List<Finder> getAllFinders() {
        return finderService.getAllFinders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Finder> getFinderById(@PathVariable Long id) {
        return finderService.getFinderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Finder createFinder(@RequestBody Finder finder) {
        return finderService.createFinder(finder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Finder> updateFinder(@PathVariable Long id, @RequestBody Finder finder) {
        try {
            return ResponseEntity.ok(finderService.updateFinder(id, finder));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFinder(@PathVariable Long id) {
        finderService.deleteFinder(id);
        return ResponseEntity.noContent().build();
    }
}