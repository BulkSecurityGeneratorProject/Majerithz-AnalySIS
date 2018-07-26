package com.empleodigital.murcia.majerithz.repository;

import com.empleodigital.murcia.majerithz.domain.ThreatSubType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ThreatSubType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThreatSubTypeRepository extends JpaRepository<ThreatSubType, Long> {

}
