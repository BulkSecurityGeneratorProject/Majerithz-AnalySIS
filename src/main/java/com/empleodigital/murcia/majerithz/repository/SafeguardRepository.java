package com.empleodigital.murcia.majerithz.repository;

import com.empleodigital.murcia.majerithz.domain.Safeguard;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Safeguard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SafeguardRepository extends JpaRepository<Safeguard, Long> {

}
