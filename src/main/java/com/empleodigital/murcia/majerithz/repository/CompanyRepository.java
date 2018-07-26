package com.empleodigital.murcia.majerithz.repository;

import com.empleodigital.murcia.majerithz.domain.Company;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Company entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

}
