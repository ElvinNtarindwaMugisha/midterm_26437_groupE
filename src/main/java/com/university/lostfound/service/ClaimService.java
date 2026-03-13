package com.university.lostfound.service;

import com.university.lostfound.model.Claim;
import com.university.lostfound.repository.ClaimRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClaimService {

    private final ClaimRepository claimRepository;

    public ClaimService(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }

    public Page<Claim> getClaims(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return claimRepository.findAll(pageable);
    }

    public Optional<Claim> getClaimById(Long id) {
        return claimRepository.findById(id);
    }
}