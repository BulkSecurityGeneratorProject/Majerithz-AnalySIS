package com.empleodigital.murcia.majerithz.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "company_name", nullable = false)
    private String companyName;

    @NotNull
    @Column(name = "company_identifier", nullable = false)
    private String companyIdentifier;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "sector")
    private String sector;

    @Column(name = "assumed_risk")
    private Double assumedRisk;

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Location> locations = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Employee> employees = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Analysis> analyses = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Asset> assets = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Company companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyIdentifier() {
        return companyIdentifier;
    }

    public Company companyIdentifier(String companyIdentifier) {
        this.companyIdentifier = companyIdentifier;
        return this;
    }

    public void setCompanyIdentifier(String companyIdentifier) {
        this.companyIdentifier = companyIdentifier;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Company creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getSector() {
        return sector;
    }

    public Company sector(String sector) {
        this.sector = sector;
        return this;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public Double getAssumedRisk() {
        return assumedRisk;
    }

    public Company assumedRisk(Double assumedRisk) {
        this.assumedRisk = assumedRisk;
        return this;
    }

    public void setAssumedRisk(Double assumedRisk) {
        this.assumedRisk = assumedRisk;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public Company locations(Set<Location> locations) {
        this.locations = locations;
        return this;
    }

    public Company addLocation(Location location) {
        this.locations.add(location);
        location.setCompany(this);
        return this;
    }

    public Company removeLocation(Location location) {
        this.locations.remove(location);
        location.setCompany(null);
        return this;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public Company employees(Set<Employee> employees) {
        this.employees = employees;
        return this;
    }

    public Company addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.setCompany(this);
        return this;
    }

    public Company removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.setCompany(null);
        return this;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Set<Analysis> getAnalyses() {
        return analyses;
    }

    public Company analyses(Set<Analysis> analyses) {
        this.analyses = analyses;
        return this;
    }

    public Company addAnalysis(Analysis analysis) {
        this.analyses.add(analysis);
        analysis.setCompany(this);
        return this;
    }

    public Company removeAnalysis(Analysis analysis) {
        this.analyses.remove(analysis);
        analysis.setCompany(null);
        return this;
    }

    public void setAnalyses(Set<Analysis> analyses) {
        this.analyses = analyses;
    }

    public Set<Asset> getAssets() {
        return assets;
    }

    public Company assets(Set<Asset> assets) {
        this.assets = assets;
        return this;
    }

    public Company addAsset(Asset asset) {
        this.assets.add(asset);
        asset.setCompany(this);
        return this;
    }

    public Company removeAsset(Asset asset) {
        this.assets.remove(asset);
        asset.setCompany(null);
        return this;
    }

    public void setAssets(Set<Asset> assets) {
        this.assets = assets;
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
        Company company = (Company) o;
        if (company.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), company.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", companyName='" + getCompanyName() + "'" +
            ", companyIdentifier='" + getCompanyIdentifier() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", sector='" + getSector() + "'" +
            ", assumedRisk=" + getAssumedRisk() +
            "}";
    }
}
