package com.empleodigital.murcia.majerithz.repository;

import com.empleodigital.murcia.majerithz.domain.ExistingSafeguards;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ExistingSafeguards entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExistingSafeguardsRepository extends JpaRepository<ExistingSafeguards, Long> {

}
