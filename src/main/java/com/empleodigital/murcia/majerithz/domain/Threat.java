package com.empleodigital.murcia.majerithz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.empleodigital.murcia.majerithz.domain.enumeration.ThreatsTypeCode;

/**
 * A Threat.
 */
@Entity
@Table(name = "threat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Threat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "threat_type_code")
    private ThreatsTypeCode threatTypeCode;

    @Column(name = "threat_name")
    private String threatName;

    @Size(max = 100)
    @Column(name = "threat_commentary", length = 100)
    private String threatCommentary;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "asset_degradation")
    private Integer assetDegradation;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "theoretical_likelihood")
    private Integer theoreticalLikelihood;

    @DecimalMin(value = "0")
    @DecimalMax(value = "5")
    @Column(name = "potential_impact")
    private Double potentialImpact;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private ThreatSubType threatSubType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("threats")
    private Asset asset;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ThreatsTypeCode getThreatTypeCode() {
        return threatTypeCode;
    }

    public Threat threatTypeCode(ThreatsTypeCode threatTypeCode) {
        this.threatTypeCode = threatTypeCode;
        return this;
    }

    public void setThreatTypeCode(ThreatsTypeCode threatTypeCode) {
        this.threatTypeCode = threatTypeCode;
    }

    public String getThreatName() {
        return threatName;
    }

    public Threat threatName(String threatName) {
        this.threatName = threatName;
        return this;
    }

    public void setThreatName(String threatName) {
        this.threatName = threatName;
    }

    public String getThreatCommentary() {
        return threatCommentary;
    }

    public Threat threatCommentary(String threatCommentary) {
        this.threatCommentary = threatCommentary;
        return this;
    }

    public void setThreatCommentary(String threatCommentary) {
        this.threatCommentary = threatCommentary;
    }

    public Integer getAssetDegradation() {
        return assetDegradation;
    }

    public Threat assetDegradation(Integer assetDegradation) {
        this.assetDegradation = assetDegradation;
        return this;
    }

    public void setAssetDegradation(Integer assetDegradation) {
        this.assetDegradation = assetDegradation;
    }

    public Integer getTheoreticalLikelihood() {
        return theoreticalLikelihood;
    }

    public Threat theoreticalLikelihood(Integer theoreticalLikelihood) {
        this.theoreticalLikelihood = theoreticalLikelihood;
        return this;
    }

    public void setTheoreticalLikelihood(Integer theoreticalLikelihood) {
        this.theoreticalLikelihood = theoreticalLikelihood;
    }

    public Double getPotentialImpact() {
        return potentialImpact;
    }

    public Threat potentialImpact(Double potentialImpact) {
        this.potentialImpact = potentialImpact;
        return this;
    }

    public void setPotentialImpact(Double potentialImpact) {
        this.potentialImpact = potentialImpact;
    }

    public ThreatSubType getThreatSubType() {
        return threatSubType;
    }

    public Threat threatSubType(ThreatSubType threatSubType) {
        this.threatSubType = threatSubType;
        return this;
    }

    public void setThreatSubType(ThreatSubType threatSubType) {
        this.threatSubType = threatSubType;
    }

    public Asset getAsset() {
        return asset;
    }

    public Threat asset(Asset asset) {
        this.asset = asset;
        return this;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
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
        Threat threat = (Threat) o;
        if (threat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), threat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Threat{" +
            "id=" + getId() +
            ", threatTypeCode='" + getThreatTypeCode() + "'" +
            ", threatName='" + getThreatName() + "'" +
            ", threatCommentary='" + getThreatCommentary() + "'" +
            ", assetDegradation=" + getAssetDegradation() +
            ", theoreticalLikelihood=" + getTheoreticalLikelihood() +
            ", potentialImpact=" + getPotentialImpact() +
            "}";
    }
}
