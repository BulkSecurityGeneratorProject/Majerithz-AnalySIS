package com.empleodigital.murcia.majerithz.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.empleodigital.murcia.majerithz.domain.enumeration.AssetsTypeCode;

/**
 * A Asset.
 */
@Entity
@Table(name = "asset")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Asset implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "asset_type_code")
    private AssetsTypeCode assetTypeCode;

    @NotNull
    @Column(name = "asset_name", nullable = false)
    private String assetName;

    @Size(max = 5000)
    @Column(name = "description_asset", length = 5000)
    private String descriptionAsset;

    @NotNull
    @Column(name = "identifier", nullable = false)
    private String identifier;

    @Column(name = "asset_location")
    private String assetLocation;

    @Column(name = "owner")
    private String owner;

    @Column(name = "responsible")
    private String responsible;

    @Column(name = "quantity")
    private Integer quantity;

    @DecimalMin(value = "0")
    @DecimalMax(value = "5")
    @Column(name = "asset_qualitative_value")
    private Double assetQualitativeValue;

    @Column(name = "asset_quantitative_value")
    private Double assetQuantitativeValue;

    @DecimalMin(value = "0")
    @DecimalMax(value = "5")
    @Column(name = "potential_risk")
    private Double potentialRisk;

    @DecimalMin(value = "0")
    @DecimalMax(value = "5")
    @Column(name = "estimated_risk")
    private Double estimatedRisk;

    @OneToMany(mappedBy = "asset")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AssetSubType> assetSubTypes = new HashSet<>();

    @OneToMany(mappedBy = "asset")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Dimension> dimensions = new HashSet<>();

    @OneToMany(mappedBy = "asset")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ExistingSafeguards> existingSafeguards = new HashSet<>();

    @OneToMany(mappedBy = "asset")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Threat> threats = new HashSet<>();

    @OneToMany(mappedBy = "asset")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Dependence> dependences = new HashSet<>();

    @OneToMany(mappedBy = "asset")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SafeguardsPackage> safeguardsPackages = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("assets")
    private Company company;

    @ManyToMany(mappedBy = "assets")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Analysis> analyses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AssetsTypeCode getAssetTypeCode() {
        return assetTypeCode;
    }

    public Asset assetTypeCode(AssetsTypeCode assetTypeCode) {
        this.assetTypeCode = assetTypeCode;
        return this;
    }

    public void setAssetTypeCode(AssetsTypeCode assetTypeCode) {
        this.assetTypeCode = assetTypeCode;
    }

    public String getAssetName() {
        return assetName;
    }

    public Asset assetName(String assetName) {
        this.assetName = assetName;
        return this;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getDescriptionAsset() {
        return descriptionAsset;
    }

    public Asset descriptionAsset(String descriptionAsset) {
        this.descriptionAsset = descriptionAsset;
        return this;
    }

    public void setDescriptionAsset(String descriptionAsset) {
        this.descriptionAsset = descriptionAsset;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Asset identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getAssetLocation() {
        return assetLocation;
    }

    public Asset assetLocation(String assetLocation) {
        this.assetLocation = assetLocation;
        return this;
    }

    public void setAssetLocation(String assetLocation) {
        this.assetLocation = assetLocation;
    }

    public String getOwner() {
        return owner;
    }

    public Asset owner(String owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getResponsible() {
        return responsible;
    }

    public Asset responsible(String responsible) {
        this.responsible = responsible;
        return this;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Asset quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getAssetQualitativeValue() {
        return assetQualitativeValue;
    }

    public Asset assetQualitativeValue(Double assetQualitativeValue) {
        this.assetQualitativeValue = assetQualitativeValue;
        return this;
    }

    public void setAssetQualitativeValue(Double assetQualitativeValue) {
        this.assetQualitativeValue = assetQualitativeValue;
    }

    public Double getAssetQuantitativeValue() {
        return assetQuantitativeValue;
    }

    public Asset assetQuantitativeValue(Double assetQuantitativeValue) {
        this.assetQuantitativeValue = assetQuantitativeValue;
        return this;
    }

    public void setAssetQuantitativeValue(Double assetQuantitativeValue) {
        this.assetQuantitativeValue = assetQuantitativeValue;
    }

    public Double getPotentialRisk() {
        return potentialRisk;
    }

    public Asset potentialRisk(Double potentialRisk) {
        this.potentialRisk = potentialRisk;
        return this;
    }

    public void setPotentialRisk(Double potentialRisk) {
        this.potentialRisk = potentialRisk;
    }

    public Double getEstimatedRisk() {
        return estimatedRisk;
    }

    public Asset estimatedRisk(Double estimatedRisk) {
        this.estimatedRisk = estimatedRisk;
        return this;
    }

    public void setEstimatedRisk(Double estimatedRisk) {
        this.estimatedRisk = estimatedRisk;
    }

    public Set<AssetSubType> getAssetSubTypes() {
        return assetSubTypes;
    }

    public Asset assetSubTypes(Set<AssetSubType> assetSubTypes) {
        this.assetSubTypes = assetSubTypes;
        return this;
    }

    public Asset addAssetSubType(AssetSubType assetSubType) {
        this.assetSubTypes.add(assetSubType);
        assetSubType.setAsset(this);
        return this;
    }

    public Asset removeAssetSubType(AssetSubType assetSubType) {
        this.assetSubTypes.remove(assetSubType);
        assetSubType.setAsset(null);
        return this;
    }

    public void setAssetSubTypes(Set<AssetSubType> assetSubTypes) {
        this.assetSubTypes = assetSubTypes;
    }

    public Set<Dimension> getDimensions() {
        return dimensions;
    }

    public Asset dimensions(Set<Dimension> dimensions) {
        this.dimensions = dimensions;
        return this;
    }

    public Asset addDimension(Dimension dimension) {
        this.dimensions.add(dimension);
        dimension.setAsset(this);
        return this;
    }

    public Asset removeDimension(Dimension dimension) {
        this.dimensions.remove(dimension);
        dimension.setAsset(null);
        return this;
    }

    public void setDimensions(Set<Dimension> dimensions) {
        this.dimensions = dimensions;
    }

    public Set<ExistingSafeguards> getExistingSafeguards() {
        return existingSafeguards;
    }

    public Asset existingSafeguards(Set<ExistingSafeguards> existingSafeguards) {
        this.existingSafeguards = existingSafeguards;
        return this;
    }

    public Asset addExistingSafeguards(ExistingSafeguards existingSafeguards) {
        this.existingSafeguards.add(existingSafeguards);
        existingSafeguards.setAsset(this);
        return this;
    }

    public Asset removeExistingSafeguards(ExistingSafeguards existingSafeguards) {
        this.existingSafeguards.remove(existingSafeguards);
        existingSafeguards.setAsset(null);
        return this;
    }

    public void setExistingSafeguards(Set<ExistingSafeguards> existingSafeguards) {
        this.existingSafeguards = existingSafeguards;
    }

    public Set<Threat> getThreats() {
        return threats;
    }

    public Asset threats(Set<Threat> threats) {
        this.threats = threats;
        return this;
    }

    public Asset addThreat(Threat threat) {
        this.threats.add(threat);
        threat.setAsset(this);
        return this;
    }

    public Asset removeThreat(Threat threat) {
        this.threats.remove(threat);
        threat.setAsset(null);
        return this;
    }

    public void setThreats(Set<Threat> threats) {
        this.threats = threats;
    }

    public Set<Dependence> getDependences() {
        return dependences;
    }

    public Asset dependences(Set<Dependence> dependences) {
        this.dependences = dependences;
        return this;
    }

    public Asset addDependence(Dependence dependence) {
        this.dependences.add(dependence);
        dependence.setAsset(this);
        return this;
    }

    public Asset removeDependence(Dependence dependence) {
        this.dependences.remove(dependence);
        dependence.setAsset(null);
        return this;
    }

    public void setDependences(Set<Dependence> dependences) {
        this.dependences = dependences;
    }

    public Set<SafeguardsPackage> getSafeguardsPackages() {
        return safeguardsPackages;
    }

    public Asset safeguardsPackages(Set<SafeguardsPackage> safeguardsPackages) {
        this.safeguardsPackages = safeguardsPackages;
        return this;
    }

    public Asset addSafeguardsPackage(SafeguardsPackage safeguardsPackage) {
        this.safeguardsPackages.add(safeguardsPackage);
        safeguardsPackage.setAsset(this);
        return this;
    }

    public Asset removeSafeguardsPackage(SafeguardsPackage safeguardsPackage) {
        this.safeguardsPackages.remove(safeguardsPackage);
        safeguardsPackage.setAsset(null);
        return this;
    }

    public void setSafeguardsPackages(Set<SafeguardsPackage> safeguardsPackages) {
        this.safeguardsPackages = safeguardsPackages;
    }

    public Company getCompany() {
        return company;
    }

    public Asset company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<Analysis> getAnalyses() {
        return analyses;
    }

    public Asset analyses(Set<Analysis> analyses) {
        this.analyses = analyses;
        return this;
    }

    public Asset addAnalysis(Analysis analysis) {
        this.analyses.add(analysis);
        analysis.getAssets().add(this);
        return this;
    }

    public Asset removeAnalysis(Analysis analysis) {
        this.analyses.remove(analysis);
        analysis.getAssets().remove(this);
        return this;
    }

    public void setAnalyses(Set<Analysis> analyses) {
        this.analyses = analyses;
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
        Asset asset = (Asset) o;
        if (asset.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), asset.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Asset{" +
            "id=" + getId() +
            ", assetTypeCode='" + getAssetTypeCode() + "'" +
            ", assetName='" + getAssetName() + "'" +
            ", descriptionAsset='" + getDescriptionAsset() + "'" +
            ", identifier='" + getIdentifier() + "'" +
            ", assetLocation='" + getAssetLocation() + "'" +
            ", owner='" + getOwner() + "'" +
            ", responsible='" + getResponsible() + "'" +
            ", quantity=" + getQuantity() +
            ", assetQualitativeValue=" + getAssetQualitativeValue() +
            ", assetQuantitativeValue=" + getAssetQuantitativeValue() +
            ", potentialRisk=" + getPotentialRisk() +
            ", estimatedRisk=" + getEstimatedRisk() +
            "}";
    }
}
