package com.empleodigital.murcia.majerithz.repository;

import com.empleodigital.murcia.majerithz.domain.AssetType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AssetType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssetTypeRepository extends JpaRepository<AssetType, Long> {

}
