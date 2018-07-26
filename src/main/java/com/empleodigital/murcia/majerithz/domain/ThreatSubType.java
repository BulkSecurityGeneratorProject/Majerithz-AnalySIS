package com.empleodigital.murcia.majerithz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.empleodigital.murcia.majerithz.domain.enumeration.ThreatsTypeCode;

/**
 * A ThreatSubType.
 */
@Entity
@Table(name = "threat_sub_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ThreatSubType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "threat_type_code")
    private ThreatsTypeCode threatTypeCode;

    @Column(name = "threat_sub_type_code")
    private String threatSubTypeCode;

    @NotNull
    @Column(name = "threat_sub_type_name", nullable = false)
    private String threatSubTypeName;

    @Size(max = 5000)
    @Column(name = "threat_sub_type_description", length = 5000)
    private String threatSubTypeDescription;

    @ManyToOne
    @JsonIgnoreProperties("threatSubTypes")
    private ThreatType threatType;

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

    public ThreatSubType threatTypeCode(ThreatsTypeCode threatTypeCode) {
        this.threatTypeCode = threatTypeCode;
        return this;
    }

    public void setThreatTypeCode(ThreatsTypeCode threatTypeCode) {
        this.threatTypeCode = threatTypeCode;
    }

    public String getThreatSubTypeCode() {
        return threatSubTypeCode;
    }

    public ThreatSubType threatSubTypeCode(String threatSubTypeCode) {
        this.threatSubTypeCode = threatSubTypeCode;
        return this;
    }

    public void setThreatSubTypeCode(String threatSubTypeCode) {
        this.threatSubTypeCode = threatSubTypeCode;
    }

    public String getThreatSubTypeName() {
        return threatSubTypeName;
    }

    public ThreatSubType threatSubTypeName(String threatSubTypeName) {
        this.threatSubTypeName = threatSubTypeName;
        return this;
    }

    public void setThreatSubTypeName(String threatSubTypeName) {
        this.threatSubTypeName = threatSubTypeName;
    }

    public String getThreatSubTypeDescription() {
        return threatSubTypeDescription;
    }

    public ThreatSubType threatSubTypeDescription(String threatSubTypeDescription) {
        this.threatSubTypeDescription = threatSubTypeDescription;
        return this;
    }

    public void setThreatSubTypeDescription(String threatSubTypeDescription) {
        this.threatSubTypeDescription = threatSubTypeDescription;
    }

    public ThreatType getThreatType() {
        return threatType;
    }

    public ThreatSubType threatType(ThreatType threatType) {
        this.threatType = threatType;
        return this;
    }

    public void setThreatType(ThreatType threatType) {
        this.threatType = threatType;
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
        ThreatSubType threatSubType = (ThreatSubType) o;
        if (threatSubType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), threatSubType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ThreatSubType{" +
            "id=" + getId() +
            ", threatTypeCode='" + getThreatTypeCode() + "'" +
            ", threatSubTypeCode='" + getThreatSubTypeCode() + "'" +
            ", threatSubTypeName='" + getThreatSubTypeName() + "'" +
            ", threatSubTypeDescription='" + getThreatSubTypeDescription() + "'" +
            "}";
    }
}
