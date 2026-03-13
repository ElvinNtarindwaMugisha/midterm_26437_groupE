package com.university.lostfound.repository;

import com.university.lostfound.model.Location;
import com.university.lostfound.model.Elocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> {
    Optional<Location> findByCode(String code);

    List<Location> findByType(Elocation type);

    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE location CASCADE", nativeQuery = true)
    void truncateTable();
}
