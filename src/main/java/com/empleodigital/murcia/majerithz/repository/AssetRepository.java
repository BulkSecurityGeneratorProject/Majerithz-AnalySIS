package com.empleodigital.murcia.majerithz.repository;

import com.empleodigital.murcia.majerithz.domain.Asset;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Asset entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

}
