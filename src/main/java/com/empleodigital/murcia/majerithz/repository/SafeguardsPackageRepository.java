package com.empleodigital.murcia.majerithz.repository;

import com.empleodigital.murcia.majerithz.domain.SafeguardsPackage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SafeguardsPackage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SafeguardsPackageRepository extends JpaRepository<SafeguardsPackage, Long> {

}
