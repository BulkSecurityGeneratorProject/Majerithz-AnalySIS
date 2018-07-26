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

import com.empleodigital.murcia.majerithz.domain.enumeration.AssetsTypeCode;

/**
 * A AssetType.
 */
@Entity
@Table(name = "asset_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AssetType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "asset_type_code")
    private AssetsTypeCode assetTypeCode;

    @NotNull
    @Column(name = "asset_type_name", nullable = false)
    private String assetTypeName;

    @Size(max = 5000)
    @Column(name = "asset_type_description", length = 5000)
    private String assetTypeDescription;

    @OneToMany(mappedBy = "assetType")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AssetSubType> assetSubTypes = new HashSet<>();

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

    public AssetType assetTypeCode(AssetsTypeCode assetTypeCode) {
        this.assetTypeCode = assetTypeCode;
        return this;
    }

    public void setAssetTypeCode(AssetsTypeCode assetTypeCode) {
        this.assetTypeCode = assetTypeCode;
    }

    public String getAssetTypeName() {
        return assetTypeName;
    }

    public AssetType assetTypeName(String assetTypeName) {
        this.assetTypeName = assetTypeName;
        return this;
    }

    public void setAssetTypeName(String assetTypeName) {
        this.assetTypeName = assetTypeName;
    }

    public String getAssetTypeDescription() {
        return assetTypeDescription;
    }

    public AssetType assetTypeDescription(String assetTypeDescription) {
        this.assetTypeDescription = assetTypeDescription;
        return this;
    }

    public void setAssetTypeDescription(String assetTypeDescription) {
        this.assetTypeDescription = assetTypeDescription;
    }

    public Set<AssetSubType> getAssetSubTypes() {
        return assetSubTypes;
    }

    public AssetType assetSubTypes(Set<AssetSubType> assetSubTypes) {
        this.assetSubTypes = assetSubTypes;
        return this;
    }

    public AssetType addAssetSubType(AssetSubType assetSubType) {
        this.assetSubTypes.add(assetSubType);
        assetSubType.setAssetType(this);
        return this;
    }

    public AssetType removeAssetSubType(AssetSubType assetSubType) {
        this.assetSubTypes.remove(assetSubType);
        assetSubType.setAssetType(null);
        return this;
    }

    public void setAssetSubTypes(Set<AssetSubType> assetSubTypes) {
        this.assetSubTypes = assetSubTypes;
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
        AssetType assetType = (AssetType) o;
        if (assetType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assetType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AssetType{" +
            "id=" + getId() +
            ", assetTypeCode='" + getAssetTypeCode() + "'" +
            ", assetTypeName='" + getAssetTypeName() + "'" +
            ", assetTypeDescription='" + getAssetTypeDescription() + "'" +
            "}";
    }
}
