package com.university.lostfound.service;

import com.university.lostfound.model.Administration;
import com.university.lostfound.model.Claim;
import com.university.lostfound.model.IDCard;
import com.university.lostfound.repository.AdministrationRepository;
import com.university.lostfound.repository.ClaimRepository;
import com.university.lostfound.repository.IDCardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AdministrationService {

    private final AdministrationRepository administrationRepository;
    private final IDCardRepository idCardRepository;
    private final ClaimRepository claimRepository;

    public AdministrationService(AdministrationRepository administrationRepository,
            IDCardRepository idCardRepository,
            ClaimRepository claimRepository) {
        this.administrationRepository = administrationRepository;
        this.idCardRepository = idCardRepository;
        this.claimRepository = claimRepository;
    }

    public List<Administration> getAllAdmins() {
        return administrationRepository.findAll();
    }

    public Optional<Administration> getAdminById(Long id) {
        return administrationRepository.findById(id);
    }

    @Transactional
    public Administration createAdmin(Administration administration) {

        IDCard idCard = idCardRepository.findByCardNumber(administration.getCardNumber())
                .orElseThrow(() -> new RuntimeException(
                        "IDCard not found with cardNumber: " + administration.getCardNumber()));

        idCard.setStatus("RETURNED");
        idCardRepository.save(idCard);

        List<Claim> claims = idCard.getClaims();
        Claim targetClaim = null;
        if (claims != null && !claims.isEmpty()) {
            targetClaim = claims.get(claims.size() - 1);
            targetClaim.setClaimType("RETURNED");
            claimRepository.save(targetClaim);
        }

        Administration savedAdmin = administrationRepository.save(administration);

        if (targetClaim != null) {
            Set<Claim> adminClaims = new HashSet<>();
            adminClaims.add(targetClaim);
            savedAdmin.setClaims(adminClaims);
            administrationRepository.save(savedAdmin);
        }

        return savedAdmin;
    }

    public Administration updateAdmin(Long id, Administration updated) {
        return administrationRepository.findById(id).map(admin -> {
            admin.setAdminName(updated.getAdminName());
            admin.setOfficeName(updated.getOfficeName());
            return administrationRepository.save(admin);
        }).orElseThrow(() -> new RuntimeException("Admin not found with id: " + id));
    }

    public void deleteAdmin(Long id) {
        administrationRepository.deleteById(id);
    }
}