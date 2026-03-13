package com.university.lostfound.repository;

import com.university.lostfound.model.Claim;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimRepository extends JpaRepository<Claim, Long> {

    Page<Claim> findAll(Pageable pageable);
}