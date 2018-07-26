package com.empleodigital.murcia.majerithz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ExistingSafeguards.
 */
@Entity
@Table(name = "existing_safeguards")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExistingSafeguards implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "existing_safeguards_name")
    private String existingSafeguardsName;

    @Size(max = 100)
    @Column(name = "existing_safeguards_commentary", length = 100)
    private String existingSafeguardsCommentary;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "effectiveness_against_degradation")
    private Integer effectivenessAgainstDegradation;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "effectiveness_against_likelihood")
    private Integer effectivenessAgainstLikelihood;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("existingSafeguards")
    private Asset asset;

    @ManyToOne
    @JsonIgnoreProperties("existingSafeguards")
    private Safeguard safeguard;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExistingSafeguardsName() {
        return existingSafeguardsName;
    }

    public ExistingSafeguards existingSafeguardsName(String existingSafeguardsName) {
        this.existingSafeguardsName = existingSafeguardsName;
        return this;
    }

    public void setExistingSafeguardsName(String existingSafeguardsName) {
        this.existingSafeguardsName = existingSafeguardsName;
    }

    public String getExistingSafeguardsCommentary() {
        return existingSafeguardsCommentary;
    }

    public ExistingSafeguards existingSafeguardsCommentary(String existingSafeguardsCommentary) {
        this.existingSafeguardsCommentary = existingSafeguardsCommentary;
        return this;
    }

    public void setExistingSafeguardsCommentary(String existingSafeguardsCommentary) {
        this.existingSafeguardsCommentary = existingSafeguardsCommentary;
    }

    public Integer getEffectivenessAgainstDegradation() {
        return effectivenessAgainstDegradation;
    }

    public ExistingSafeguards effectivenessAgainstDegradation(Integer effectivenessAgainstDegradation) {
        this.effectivenessAgainstDegradation = effectivenessAgainstDegradation;
        return this;
    }

    public void setEffectivenessAgainstDegradation(Integer effectivenessAgainstDegradation) {
        this.effectivenessAgainstDegradation = effectivenessAgainstDegradation;
    }

    public Integer getEffectivenessAgainstLikelihood() {
        return effectivenessAgainstLikelihood;
    }

    public ExistingSafeguards effectivenessAgainstLikelihood(Integer effectivenessAgainstLikelihood) {
        this.effectivenessAgainstLikelihood = effectivenessAgainstLikelihood;
        return this;
    }

    public void setEffectivenessAgainstLikelihood(Integer effectivenessAgainstLikelihood) {
        this.effectivenessAgainstLikelihood = effectivenessAgainstLikelihood;
    }

    public Asset getAsset() {
        return asset;
    }

    public ExistingSafeguards asset(Asset asset) {
        this.asset = asset;
        return this;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public Safeguard getSafeguard() {
        return safeguard;
    }

    public ExistingSafeguards safeguard(Safeguard safeguard) {
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
        ExistingSafeguards existingSafeguards = (ExistingSafeguards) o;
        if (existingSafeguards.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), existingSafeguards.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExistingSafeguards{" +
            "id=" + getId() +
            ", existingSafeguardsName='" + getExistingSafeguardsName() + "'" +
            ", existingSafeguardsCommentary='" + getExistingSafeguardsCommentary() + "'" +
            ", effectivenessAgainstDegradation=" + getEffectivenessAgainstDegradation() +
            ", effectivenessAgainstLikelihood=" + getEffectivenessAgainstLikelihood() +
            "}";
    }
}
