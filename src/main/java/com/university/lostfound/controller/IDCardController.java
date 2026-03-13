package com.university.lostfound.controller;

import com.university.lostfound.model.IDCard;
import com.university.lostfound.service.IDCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/idcards")
public class IDCardController {

    private final IDCardService idCardService;

    public IDCardController(IDCardService idCardService) {
        this.idCardService = idCardService;
    }

    @GetMapping
    public List<IDCard> getAllIDCards() {
        return idCardService.getAllIDCards();
    }

    @GetMapping("/{id}")
    public ResponseEntity<IDCard> getIDCardById(@PathVariable Long id) {
        return idCardService.getIDCardById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public IDCard createIDCard(@RequestBody IDCard idCard) {
        return idCardService.createIDCard(idCard);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IDCard> updateIDCard(@PathVariable Long id, @RequestBody IDCard idCard) {
        try {
            return ResponseEntity.ok(idCardService.updateIDCard(id, idCard));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIDCard(@PathVariable Long id) {
        idCardService.deleteIDCard(id);
        return ResponseEntity.noContent().build();
    }
}