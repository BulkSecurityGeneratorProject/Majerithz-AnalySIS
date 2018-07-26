package com.empleodigital.murcia.majerithz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.empleodigital.murcia.majerithz.domain.enumeration.AssetsTypeCode;

/**
 * A AssetSubType.
 */
@Entity
@Table(name = "asset_sub_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AssetSubType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "asset_type_code")
    private AssetsTypeCode assetTypeCode;

    @Column(name = "asset_sub_type_code")
    private String assetSubTypeCode;

    @NotNull
    @Column(name = "asset_sub_type_name", nullable = false)
    private String assetSubTypeName;

    @Size(max = 5000)
    @Column(name = "asset_sub_type_description", length = 5000)
    private String assetSubTypeDescription;

    @ManyToOne
    @JsonIgnoreProperties("assetSubTypes")
    private Asset asset;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("assetSubTypes")
    private AssetType assetType;

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

    public AssetSubType assetTypeCode(AssetsTypeCode assetTypeCode) {
        this.assetTypeCode = assetTypeCode;
        return this;
    }

    public void setAssetTypeCode(AssetsTypeCode assetTypeCode) {
        this.assetTypeCode = assetTypeCode;
    }

    public String getAssetSubTypeCode() {
        return assetSubTypeCode;
    }

    public AssetSubType assetSubTypeCode(String assetSubTypeCode) {
        this.assetSubTypeCode = assetSubTypeCode;
        return this;
    }

    public void setAssetSubTypeCode(String assetSubTypeCode) {
        this.assetSubTypeCode = assetSubTypeCode;
    }

    public String getAssetSubTypeName() {
        return assetSubTypeName;
    }

    public AssetSubType assetSubTypeName(String assetSubTypeName) {
        this.assetSubTypeName = assetSubTypeName;
        return this;
    }

    public void setAssetSubTypeName(String assetSubTypeName) {
        this.assetSubTypeName = assetSubTypeName;
    }

    public String getAssetSubTypeDescription() {
        return assetSubTypeDescription;
    }

    public AssetSubType assetSubTypeDescription(String assetSubTypeDescription) {
        this.assetSubTypeDescription = assetSubTypeDescription;
        return this;
    }

    public void setAssetSubTypeDescription(String assetSubTypeDescription) {
        this.assetSubTypeDescription = assetSubTypeDescription;
    }

    public Asset getAsset() {
        return asset;
    }

    public AssetSubType asset(Asset asset) {
        this.asset = asset;
        return this;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public AssetType getAssetType() {
        return assetType;
    }

    public AssetSubType assetType(AssetType assetType) {
        this.assetType = assetType;
        return this;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
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
        AssetSubType assetSubType = (AssetSubType) o;
        if (assetSubType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assetSubType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AssetSubType{" +
            "id=" + getId() +
            ", assetTypeCode='" + getAssetTypeCode() + "'" +
            ", assetSubTypeCode='" + getAssetSubTypeCode() + "'" +
            ", assetSubTypeName='" + getAssetSubTypeName() + "'" +
            ", assetSubTypeDescription='" + getAssetSubTypeDescription() + "'" +
            "}";
    }
}
