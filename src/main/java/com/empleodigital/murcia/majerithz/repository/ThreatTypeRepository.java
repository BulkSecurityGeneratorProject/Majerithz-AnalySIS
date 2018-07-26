package com.empleodigital.murcia.majerithz.repository;

import com.empleodigital.murcia.majerithz.domain.ThreatType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ThreatType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThreatTypeRepository extends JpaRepository<ThreatType, Long> {

}
