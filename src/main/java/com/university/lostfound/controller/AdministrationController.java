package com.university.lostfound.controller;

import com.university.lostfound.model.Administration;
import com.university.lostfound.service.AdministrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/administration")
public class AdministrationController {

    private final AdministrationService administrationService;

    public AdministrationController(AdministrationService administrationService) {
        this.administrationService = administrationService;
    }

    @GetMapping
    public List<Administration> getAllAdmins() {
        return administrationService.getAllAdmins();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Administration> getAdminById(@PathVariable Long id) {
        return administrationService.getAdminById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Administration createAdmin(@RequestBody Administration administration) {
        return administrationService.createAdmin(administration);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Administration> updateAdmin(@PathVariable Long id, @RequestBody Administration administration) {
        try {
            return ResponseEntity.ok(administrationService.updateAdmin(id, administration));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        administrationService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }
}