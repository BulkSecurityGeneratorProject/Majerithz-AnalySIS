package com.empleodigital.murcia.majerithz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.empleodigital.murcia.majerithz.domain.enumeration.DimensionsTypeCode;

/**
 * A Dimension.
 */
@Entity
@Table(name = "dimension")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Dimension implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "dimension_type_code")
    private DimensionsTypeCode dimensionTypeCode;

    @Column(name = "dimension_dimension")
    private String dimensionDimension;

    @Size(max = 5000)
    @Column(name = "dimension_description", length = 5000)
    private String dimensionDescription;

    @ManyToOne
    @JsonIgnoreProperties("dimensions")
    private Asset asset;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DimensionsTypeCode getDimensionTypeCode() {
        return dimensionTypeCode;
    }

    public Dimension dimensionTypeCode(DimensionsTypeCode dimensionTypeCode) {
        this.dimensionTypeCode = dimensionTypeCode;
        return this;
    }

    public void setDimensionTypeCode(DimensionsTypeCode dimensionTypeCode) {
        this.dimensionTypeCode = dimensionTypeCode;
    }

    public String getDimensionDimension() {
        return dimensionDimension;
    }

    public Dimension dimensionDimension(String dimensionDimension) {
        this.dimensionDimension = dimensionDimension;
        return this;
    }

    public void setDimensionDimension(String dimensionDimension) {
        this.dimensionDimension = dimensionDimension;
    }

    public String getDimensionDescription() {
        return dimensionDescription;
    }

    public Dimension dimensionDescription(String dimensionDescription) {
        this.dimensionDescription = dimensionDescription;
        return this;
    }

    public void setDimensionDescription(String dimensionDescription) {
        this.dimensionDescription = dimensionDescription;
    }

    public Asset getAsset() {
        return asset;
    }

    public Dimension asset(Asset asset) {
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
        Dimension dimension = (Dimension) o;
        if (dimension.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dimension.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Dimension{" +
            "id=" + getId() +
            ", dimensionTypeCode='" + getDimensionTypeCode() + "'" +
            ", dimensionDimension='" + getDimensionDimension() + "'" +
            ", dimensionDescription='" + getDimensionDescription() + "'" +
            "}";
    }
}
