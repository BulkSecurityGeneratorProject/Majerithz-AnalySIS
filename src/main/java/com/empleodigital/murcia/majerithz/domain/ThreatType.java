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

import com.empleodigital.murcia.majerithz.domain.enumeration.ThreatsTypeCode;

/**
 * A ThreatType.
 */
@Entity
@Table(name = "threat_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ThreatType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "threat_type_code")
    private ThreatsTypeCode threatTypeCode;

    @NotNull
    @Column(name = "threat_type_name", nullable = false)
    private String threatTypeName;

    @Size(max = 5000)
    @Column(name = "threat_type_description", length = 5000)
    private String threatTypeDescription;

    @OneToMany(mappedBy = "threatType")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ThreatSubType> threatSubTypes = new HashSet<>();

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

    public ThreatType threatTypeCode(ThreatsTypeCode threatTypeCode) {
        this.threatTypeCode = threatTypeCode;
        return this;
    }

    public void setThreatTypeCode(ThreatsTypeCode threatTypeCode) {
        this.threatTypeCode = threatTypeCode;
    }

    public String getThreatTypeName() {
        return threatTypeName;
    }

    public ThreatType threatTypeName(String threatTypeName) {
        this.threatTypeName = threatTypeName;
        return this;
    }

    public void setThreatTypeName(String threatTypeName) {
        this.threatTypeName = threatTypeName;
    }

    public String getThreatTypeDescription() {
        return threatTypeDescription;
    }

    public ThreatType threatTypeDescription(String threatTypeDescription) {
        this.threatTypeDescription = threatTypeDescription;
        return this;
    }

    public void setThreatTypeDescription(String threatTypeDescription) {
        this.threatTypeDescription = threatTypeDescription;
    }

    public Set<ThreatSubType> getThreatSubTypes() {
        return threatSubTypes;
    }

    public ThreatType threatSubTypes(Set<ThreatSubType> threatSubTypes) {
        this.threatSubTypes = threatSubTypes;
        return this;
    }

    public ThreatType addThreatSubType(ThreatSubType threatSubType) {
        this.threatSubTypes.add(threatSubType);
        threatSubType.setThreatType(this);
        return this;
    }

    public ThreatType removeThreatSubType(ThreatSubType threatSubType) {
        this.threatSubTypes.remove(threatSubType);
        threatSubType.setThreatType(null);
        return this;
    }

    public void setThreatSubTypes(Set<ThreatSubType> threatSubTypes) {
        this.threatSubTypes = threatSubTypes;
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
        ThreatType threatType = (ThreatType) o;
        if (threatType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), threatType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ThreatType{" +
            "id=" + getId() +
            ", threatTypeCode='" + getThreatTypeCode() + "'" +
            ", threatTypeName='" + getThreatTypeName() + "'" +
            ", threatTypeDescription='" + getThreatTypeDescription() + "'" +
            "}";
    }
}
