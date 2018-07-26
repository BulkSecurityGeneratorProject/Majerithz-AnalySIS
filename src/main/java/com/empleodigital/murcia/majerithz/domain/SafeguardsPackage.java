package com.empleodigital.murcia.majerithz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SafeguardsPackage.
 */
@Entity
@Table(name = "safeguards_package")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SafeguardsPackage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "safeguards_package_name")
    private String safeguardsPackageName;

    @Size(max = 100)
    @Column(name = "safeguards_package_commentary", length = 100)
    private String safeguardsPackageCommentary;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "effectiveness_against_degradation")
    private Integer effectivenessAgainstDegradation;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "effectiveness_against_likelihood")
    private Integer effectivenessAgainstLikelihood;

    @ManyToOne
    @JsonIgnoreProperties("safeguardsPackages")
    private Asset asset;

    @ManyToOne
    @JsonIgnoreProperties("safeguardsPackages")
    private Safeguard safeguard;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSafeguardsPackageName() {
        return safeguardsPackageName;
    }

    public SafeguardsPackage safeguardsPackageName(String safeguardsPackageName) {
        this.safeguardsPackageName = safeguardsPackageName;
        return this;
    }

    public void setSafeguardsPackageName(String safeguardsPackageName) {
        this.safeguardsPackageName = safeguardsPackageName;
    }

    public String getSafeguardsPackageCommentary() {
        return safeguardsPackageCommentary;
    }

    public SafeguardsPackage safeguardsPackageCommentary(String safeguardsPackageCommentary) {
        this.safeguardsPackageCommentary = safeguardsPackageCommentary;
        return this;
    }

    public void setSafeguardsPackageCommentary(String safeguardsPackageCommentary) {
        this.safeguardsPackageCommentary = safeguardsPackageCommentary;
    }

    public Integer getEffectivenessAgainstDegradation() {
        return effectivenessAgainstDegradation;
    }

    public SafeguardsPackage effectivenessAgainstDegradation(Integer effectivenessAgainstDegradation) {
        this.effectivenessAgainstDegradation = effectivenessAgainstDegradation;
        return this;
    }

    public void setEffectivenessAgainstDegradation(Integer effectivenessAgainstDegradation) {
        this.effectivenessAgainstDegradation = effectivenessAgainstDegradation;
    }

    public Integer getEffectivenessAgainstLikelihood() {
        return effectivenessAgainstLikelihood;
    }

    public SafeguardsPackage effectivenessAgainstLikelihood(Integer effectivenessAgainstLikelihood) {
        this.effectivenessAgainstLikelihood = effectivenessAgainstLikelihood;
        return this;
    }

    public void setEffectivenessAgainstLikelihood(Integer effectivenessAgainstLikelihood) {
        this.effectivenessAgainstLikelihood = effectivenessAgainstLikelihood;
    }

    public Asset getAsset() {
        return asset;
    }

    public SafeguardsPackage asset(Asset asset) {
        this.asset = asset;
        return this;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public Safeguard getSafeguard() {
        return safeguard;
    }

    public SafeguardsPackage safeguard(Safeguard safeguard) {
        this.safeguard = safeguard;
        return this;
    }

    public void setSafeguard(Safeguard safeguard) {
        this.safeguard = safeguard;
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
        SafeguardsPackage safeguardsPackage = (SafeguardsPackage) o;
        if (safeguardsPackage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), safeguardsPackage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SafeguardsPackage{" +
            "id=" + getId() +
            ", safeguardsPackageName='" + getSafeguardsPackageName() + "'" +
            ", safeguardsPackageCommentary='" + getSafeguardsPackageCommentary() + "'" +
            ", effectivenessAgainstDegradation=" + getEffectivenessAgainstDegradation() +
            ", effectivenessAgainstLikelihood=" + getEffectivenessAgainstLikelihood() +
            "}";
    }
}
