package com.university.lostfound.repository;

import com.university.lostfound.model.IDCard;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IDCardRepository extends JpaRepository<IDCard, Long> {

    boolean existsByCardNumber(String cardNumber);

    Optional<IDCard> findByCardNumber(String cardNumber);
}