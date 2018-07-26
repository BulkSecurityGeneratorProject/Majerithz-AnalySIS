package com.empleodigital.murcia.majerithz.repository;

import com.empleodigital.murcia.majerithz.domain.SafeguardSubType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SafeguardSubType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SafeguardSubTypeRepository extends JpaRepository<SafeguardSubType, Long> {

}
