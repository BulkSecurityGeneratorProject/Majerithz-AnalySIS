package com.empleodigital.murcia.majerithz.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.empleodigital.murcia.majerithz.domain.enumeration.SafeguardsTypeCode;

/**
 * A Safeguard.
 */
@Entity
@Table(name = "safeguard")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Safeguard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "safeguards_type_code")
    private SafeguardsTypeCode safeguardsTypeCode;

    @Column(name = "safeguard_name")
    private String safeguardName;

    @Size(max = 5000)
    @Column(name = "safeguard_commentary", length = 5000)
    private String safeguardCommentary;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "effectiveness_against_degradation")
    private Integer effectivenessAgainstDegradation;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "effectiveness_against_likelihood")
    private Integer effectivenessAgainstLikelihood;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private SafeguardSubType safeguardSubType;

    @OneToMany(mappedBy = "safeguard")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SafeguardsPackage> safeguardsPackages = new HashSet<>();

    @OneToMany(mappedBy = "safeguard")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ExistingSafeguards> existingSafeguards = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SafeguardsTypeCode getSafeguardsTypeCode() {
        return safeguardsTypeCode;
    }

    public Safeguard safeguardsTypeCode(SafeguardsTypeCode safeguardsTypeCode) {
        this.safeguardsTypeCode = safeguardsTypeCode;
        return this;
    }

    public void setSafeguardsTypeCode(SafeguardsTypeCode safeguardsTypeCode) {
        this.safeguardsTypeCode = safeguardsTypeCode;
    }

    public String getSafeguardName() {
        return safeguardName;
    }

    public Safeguard safeguardName(String safeguardName) {
        this.safeguardName = safeguardName;
        return this;
    }

    public void setSafeguardName(String safeguardName) {
        this.safeguardName = safeguardName;
    }

    public String getSafeguardCommentary() {
        return safeguardCommentary;
    }

    public Safeguard safeguardCommentary(String safeguardCommentary) {
        this.safeguardCommentary = safeguardCommentary;
        return this;
    }

    public void setSafeguardCommentary(String safeguardCommentary) {
        this.safeguardCommentary = safeguardCommentary;
    }

    public Integer getEffectivenessAgainstDegradation() {
        return effectivenessAgainstDegradation;
    }

    public Safeguard effectivenessAgainstDegradation(Integer effectivenessAgainstDegradation) {
        this.effectivenessAgainstDegradation = effectivenessAgainstDegradation;
        return this;
    }

    public void setEffectivenessAgainstDegradation(Integer effectivenessAgainstDegradation) {
        this.effectivenessAgainstDegradation = effectivenessAgainstDegradation;
    }

    public Integer getEffectivenessAgainstLikelihood() {
        return effectivenessAgainstLikelihood;
    }

    public Safeguard effectivenessAgainstLikelihood(Integer effectivenessAgainstLikelihood) {
        this.effectivenessAgainstLikelihood = effectivenessAgainstLikelihood;
        return this;
    }

    public void setEffectivenessAgainstLikelihood(Integer effectivenessAgainstLikelihood) {
        this.effectivenessAgainstLikelihood = effectivenessAgainstLikelihood;
    }

    public SafeguardSubType getSafeguardSubType() {
        return safeguardSubType;
    }

    public Safeguard safeguardSubType(SafeguardSubType safeguardSubType) {
        this.safeguardSubType = safeguardSubType;
        return this;
    }

    public void setSafeguardSubType(SafeguardSubType safeguardSubType) {
        this.safeguardSubType = safeguardSubType;
    }

    public Set<SafeguardsPackage> getSafeguardsPackages() {
        return safeguardsPackages;
    }

    public Safeguard safeguardsPackages(Set<SafeguardsPackage> safeguardsPackages) {
        this.safeguardsPackages = safeguardsPackages;
        return this;
    }

    public Safeguard addSafeguardsPackage(SafeguardsPackage safeguardsPackage) {
        this.safeguardsPackages.add(safeguardsPackage);
        safeguardsPackage.setSafeguard(this);
        return this;
    }

    public Safeguard removeSafeguardsPackage(SafeguardsPackage safeguardsPackage) {
        this.safeguardsPackages.remove(safeguardsPackage);
        safeguardsPackage.setSafeguard(null);
        return this;
    }

    public void setSafeguardsPackages(Set<SafeguardsPackage> safeguardsPackages) {
        this.safeguardsPackages = safeguardsPackages;
    }

    public Set<ExistingSafeguards> getExistingSafeguards() {
        return existingSafeguards;
    }

    public Safeguard existingSafeguards(Set<ExistingSafeguards> existingSafeguards) {
        this.existingSafeguards = existingSafeguards;
        return this;
    }

    public Safeguard addExistingSafeguards(ExistingSafeguards existingSafeguards) {
        this.existingSafeguards.add(existingSafeguards);
        existingSafeguards.setSafeguard(this);
        return this;
    }

    public Safeguard removeExistingSafeguards(ExistingSafeguards existingSafeguards) {
        this.existingSafeguards.remove(existingSafeguards);
        existingSafeguards.setSafeguard(null);
        return this;
    }

    public void setExistingSafeguards(Set<ExistingSafeguards> existingSafeguards) {
        this.existingSafeguards = existingSafeguards;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Safeguard safeguard = (Safeguard) o;
        if (safeguard.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), safeguard.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Safeguard{" +
            "id=" + getId() +
            ", safeguardsTypeCode='" + getSafeguardsTypeCode() + "'" +
            ", safeguardName='" + getSafeguardName() + "'" +
            ", safeguardCommentary='" + getSafeguardCommentary() + "'" +
            ", effectivenessAgainstDegradation=" + getEffectivenessAgainstDegradation() +
            ", effectivenessAgainstLikelihood=" + getEffectivenessAgainstLikelihood() +
            "}";
    }
}
