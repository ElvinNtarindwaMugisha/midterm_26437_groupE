package com.university.lostfound.model;

import jakarta.persistence.*;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "claims")
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long claimId;

    private String claimType;
    private String foundLocation;

    @ManyToOne
    @JoinColumn(name = "idcard_id")
    @JsonBackReference
    private IDCard idCard;

    @ManyToMany(mappedBy = "claims")
    @JsonIgnore
    private Set<Administration> administrations;

    @ManyToOne
    @JoinColumn(name = "finder_id")
    @JsonIgnore
    private Finder finder;

    public Long getClaimId() { return claimId; }
    public void setClaimId(Long claimId) { this.claimId = claimId; }

    public String getClaimType() { return claimType; }
    public void setClaimType(String claimType) { this.claimType = claimType; }

    public String getFoundLocation() { return foundLocation; }
    public void setFoundLocation(String foundLocation) { this.foundLocation = foundLocation; }

    public IDCard getIdCard() { return idCard; }
    public void setIdCard(IDCard idCard) { this.idCard = idCard; }

    public Set<Administration> getAdministrations() { return administrations; }
    public void setAdministrations(Set<Administration> administrations) { this.administrations = administrations; }

    public Finder getFinder() { return finder; }
    public void setFinder(Finder finder) { this.finder = finder; }
}