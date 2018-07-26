package com.empleodigital.murcia.majerithz.repository;

import com.empleodigital.murcia.majerithz.domain.Analysis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Analysis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnalysisRepository extends JpaRepository<Analysis, Long> {

    @Query(value = "select distinct analysis from Analysis analysis left join fetch analysis.assets",
        countQuery = "select count(distinct analysis) from Analysis analysis")
    Page<Analysis> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct analysis from Analysis analysis left join fetch analysis.assets")
    List<Analysis> findAllWithEagerRelationships();

    @Query("select analysis from Analysis analysis left join fetch analysis.assets where analysis.id =:id")
    Optional<Analysis> findOneWithEagerRelationships(@Param("id") Long id);

}
