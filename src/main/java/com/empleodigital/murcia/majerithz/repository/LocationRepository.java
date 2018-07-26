package com.empleodigital.murcia.majerithz.repository;

import com.empleodigital.murcia.majerithz.domain.Location;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Location entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

}
