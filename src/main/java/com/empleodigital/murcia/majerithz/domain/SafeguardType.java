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
 * A SafeguardType.
 */
@Entity
@Table(name = "safeguard_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SafeguardType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "safeguards_type_code")
    private SafeguardsTypeCode safeguardsTypeCode;

    @NotNull
    @Column(name = "safeguard_type_name", nullable = false)
    private String safeguardTypeName;

    @Size(max = 5000)
    @Column(name = "safeguard_type_description", length = 5000)
    private String safeguardTypeDescription;

    @OneToMany(mappedBy = "safeguardType")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SafeguardSubType> safeguardSubTypes = new HashSet<>();

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

    public SafeguardType safeguardsTypeCode(SafeguardsTypeCode safeguardsTypeCode) {
        this.safeguardsTypeCode = safeguardsTypeCode;
        return this;
    }

    public void setSafeguardsTypeCode(SafeguardsTypeCode safeguardsTypeCode) {
        this.safeguardsTypeCode = safeguardsTypeCode;
    }

    public String getSafeguardTypeName() {
        return safeguardTypeName;
    }

    public SafeguardType safeguardTypeName(String safeguardTypeName) {
        this.safeguardTypeName = safeguardTypeName;
        return this;
    }

    public void setSafeguardTypeName(String safeguardTypeName) {
        this.safeguardTypeName = safeguardTypeName;
    }

    public String getSafeguardTypeDescription() {
        return safeguardTypeDescription;
    }

    public SafeguardType safeguardTypeDescription(String safeguardTypeDescription) {
        this.safeguardTypeDescription = safeguardTypeDescription;
        return this;
    }

    public void setSafeguardTypeDescription(String safeguardTypeDescription) {
        this.safeguardTypeDescription = safeguardTypeDescription;
    }

    public Set<SafeguardSubType> getSafeguardSubTypes() {
        return safeguardSubTypes;
    }

    public SafeguardType safeguardSubTypes(Set<SafeguardSubType> safeguardSubTypes) {
        this.safeguardSubTypes = safeguardSubTypes;
        return this;
    }

    public SafeguardType addSafeguardSubType(SafeguardSubType safeguardSubType) {
        this.safeguardSubTypes.add(safeguardSubType);
        safeguardSubType.setSafeguardType(this);
        return this;
    }

    public SafeguardType removeSafeguardSubType(SafeguardSubType safeguardSubType) {
        this.safeguardSubTypes.remove(safeguardSubType);
        safeguardSubType.setSafeguardType(null);
        return this;
    }

    public void setSafeguardSubTypes(Set<SafeguardSubType> safeguardSubTypes) {
        this.safeguardSubTypes = safeguardSubTypes;
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
        SafeguardType safeguardType = (SafeguardType) o;
        if (safeguardType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), safeguardType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SafeguardType{" +
            "id=" + getId() +
            ", safeguardsTypeCode='" + getSafeguardsTypeCode() + "'" +
            ", safeguardTypeName='" + getSafeguardTypeName() + "'" +
            ", safeguardTypeDescription='" + getSafeguardTypeDescription() + "'" +
            "}";
    }
}
