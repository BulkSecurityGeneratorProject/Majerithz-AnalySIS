package com.empleodigital.murcia.majerithz.repository;

import com.empleodigital.murcia.majerithz.domain.AssetSubType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AssetSubType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssetSubTypeRepository extends JpaRepository<AssetSubType, Long> {

}
