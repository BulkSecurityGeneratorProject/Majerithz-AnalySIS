package com.empleodigital.murcia.majerithz.repository;

import com.empleodigital.murcia.majerithz.domain.Dependence;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Dependence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DependenceRepository extends JpaRepository<Dependence, Long> {

}
