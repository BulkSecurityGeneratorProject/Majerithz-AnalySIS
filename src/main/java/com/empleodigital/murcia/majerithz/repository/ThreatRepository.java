package com.empleodigital.murcia.majerithz.repository;

import com.empleodigital.murcia.majerithz.domain.Threat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Threat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThreatRepository extends JpaRepository<Threat, Long> {

}
