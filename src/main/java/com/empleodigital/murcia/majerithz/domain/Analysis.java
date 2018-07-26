package com.empleodigital.murcia.majerithz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Analysis.
 */
@Entity
@Table(name = "analysis")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Analysis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "analysis_creation_date")
    private Instant analysisCreationDate;

    @Column(name = "analysis_last_edit")
    private Instant analysisLastEdit;

    @Column(name = "step")
    private String step;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "analysis_asset",
               joinColumns = @JoinColumn(name = "analyses_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "assets_id", referencedColumnName = "id"))
    private Set<Asset> assets = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("analyses")
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Analysis identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Instant getAnalysisCreationDate() {
        return analysisCreationDate;
    }

    public Analysis analysisCreationDate(Instant analysisCreationDate) {
        this.analysisCreationDate = analysisCreationDate;
        return this;
    }

    public void setAnalysisCreationDate(Instant analysisCreationDate) {
        this.analysisCreationDate = analysisCreationDate;
    }

    public Instant getAnalysisLastEdit() {
        return analysisLastEdit;
    }

    public Analysis analysisLastEdit(Instant analysisLastEdit) {
        this.analysisLastEdit = analysisLastEdit;
        return this;
    }

    public void setAnalysisLastEdit(Instant analysisLastEdit) {
        this.analysisLastEdit = analysisLastEdit;
    }

    public String getStep() {
        return step;
    }

    public Analysis step(String step) {
        this.step = step;
        return this;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public Set<Asset> getAssets() {
        return assets;
    }

    public Analysis assets(Set<Asset> assets) {
        this.assets = assets;
        return this;
    }

    public Analysis addAsset(Asset asset) {
        this.assets.add(asset);
        asset.getAnalyses().add(this);
        return this;
    }

    public Analysis removeAsset(Asset asset) {
        this.assets.remove(asset);
        asset.getAnalyses().remove(this);
        return this;
    }

    public void setAssets(Set<Asset> assets) {
        this.assets = assets;
    }

    public Company getCompany() {
        return company;
    }

    public Analysis company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
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
        Analysis analysis = (Analysis) o;
        if (analysis.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), analysis.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Analysis{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", analysisCreationDate='" + getAnalysisCreationDate() + "'" +
            ", analysisLastEdit='" + getAnalysisLastEdit() + "'" +
            ", step='" + getStep() + "'" +
            "}";
    }
}
