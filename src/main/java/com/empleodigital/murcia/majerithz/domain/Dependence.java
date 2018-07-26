package com.empleodigital.murcia.majerithz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Dependence.
 */
@Entity
@Table(name = "dependence")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Dependence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "jhi_degree")
    private Integer degree;

    @Size(max = 5000)
    @Column(name = "reason", length = 5000)
    private String reason;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("dependences")
    private Asset asset;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDegree() {
        return degree;
    }

    public Dependence degree(Integer degree) {
        this.degree = degree;
        return this;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public String getReason() {
        return reason;
    }

    public Dependence reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Asset getAsset() {
        return asset;
    }

    public Dependence asset(Asset asset) {
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
        Dependence dependence = (Dependence) o;
        if (dependence.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dependence.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Dependence{" +
            "id=" + getId() +
            ", degree=" + getDegree() +
            ", reason='" + getReason() + "'" +
            "}";
    }
}
