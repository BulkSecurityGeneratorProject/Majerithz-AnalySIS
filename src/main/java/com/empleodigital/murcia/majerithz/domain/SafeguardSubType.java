package com.empleodigital.murcia.majerithz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.empleodigital.murcia.majerithz.domain.enumeration.SafeguardsTypeCode;

/**
 * A SafeguardSubType.
 */
@Entity
@Table(name = "safeguard_sub_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SafeguardSubType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "safeguards_type_code")
    private SafeguardsTypeCode safeguardsTypeCode;

    @Column(name = "code_safeguard_sub_type")
    private String codeSafeguardSubType;

    @NotNull
    @Column(name = "safeguard_sub_type_name", nullable = false)
    private String safeguardSubTypeName;

    @Size(max = 5000)
    @Column(name = "safeguard_sub_type_description", length = 5000)
    private String safeguardSubTypeDescription;

    @ManyToOne
    @JsonIgnoreProperties("safeguardSubTypes")
    private SafeguardType safeguardType;

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

    public SafeguardSubType safeguardsTypeCode(SafeguardsTypeCode safeguardsTypeCode) {
        this.safeguardsTypeCode = safeguardsTypeCode;
        return this;
    }

    public void setSafeguardsTypeCode(SafeguardsTypeCode safeguardsTypeCode) {
        this.safeguardsTypeCode = safeguardsTypeCode;
    }

    public String getCodeSafeguardSubType() {
        return codeSafeguardSubType;
    }

    public SafeguardSubType codeSafeguardSubType(String codeSafeguardSubType) {
        this.codeSafeguardSubType = codeSafeguardSubType;
        return this;
    }

    public void setCodeSafeguardSubType(String codeSafeguardSubType) {
        this.codeSafeguardSubType = codeSafeguardSubType;
    }

    public String getSafeguardSubTypeName() {
        return safeguardSubTypeName;
    }

    public SafeguardSubType safeguardSubTypeName(String safeguardSubTypeName) {
        this.safeguardSubTypeName = safeguardSubTypeName;
        return this;
    }

    public void setSafeguardSubTypeName(String safeguardSubTypeName) {
        this.safeguardSubTypeName = safeguardSubTypeName;
    }

    public String getSafeguardSubTypeDescription() {
        return safeguardSubTypeDescription;
    }

    public SafeguardSubType safeguardSubTypeDescription(String safeguardSubTypeDescription) {
        this.safeguardSubTypeDescription = safeguardSubTypeDescription;
        return this;
    }

    public void setSafeguardSubTypeDescription(String safeguardSubTypeDescription) {
        this.safeguardSubTypeDescription = safeguardSubTypeDescription;
    }

    public SafeguardType getSafeguardType() {
        return safeguardType;
    }

    public SafeguardSubType safeguardType(SafeguardType safeguardType) {
        this.safeguardType = safeguardType;
        return this;
    }

    public void setSafeguardType(SafeguardType safeguardType) {
        this.safeguardType = safeguardType;
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
        SafeguardSubType safeguardSubType = (SafeguardSubType) o;
        if (safeguardSubType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), safeguardSubType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SafeguardSubType{" +
            "id=" + getId() +
            ", safeguardsTypeCode='" + getSafeguardsTypeCode() + "'" +
            ", codeSafeguardSubType='" + getCodeSafeguardSubType() + "'" +
            ", safeguardSubTypeName='" + getSafeguardSubTypeName() + "'" +
            ", safeguardSubTypeDescription='" + getSafeguardSubTypeDescription() + "'" +
            "}";
    }
}
