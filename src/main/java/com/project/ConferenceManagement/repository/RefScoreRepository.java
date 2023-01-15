package com.project.ConferenceManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.ConferenceManagement.entity.RefereeScoreEntity;

@Repository
public interface RefScoreRepository extends JpaRepository<RefereeScoreEntity, Long>{

	@Query(nativeQuery = true, value="select * from refere_score where assignment_id=:ass_id ")
	RefereeScoreEntity findByAssignmentId(@Param("ass_id") Long id);

}
