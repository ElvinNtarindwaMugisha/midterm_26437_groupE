package com.university.lostfound.repository;

import com.university.lostfound.model.Finder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Long> {
}