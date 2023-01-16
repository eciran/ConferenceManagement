package com.project.ConferenceManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.ConferenceManagement.entity.AssignmentEntity;

@Repository
public interface AssignmentRepository extends JpaRepository<AssignmentEntity, Long > {

	@Query(nativeQuery = true, value="select * from assignment where evaluation_id =:eva ")
	List<AssignmentEntity> findAllRefForEvaluation(@Param("eva") Long eva_id);
	
	@Query(nativeQuery = true, value="select * from assignment where user_id=:user_id")
	List<AssignmentEntity> findAllEvaluationForScoring(@Param("user_id") Long id);

	@Query(nativeQuery = true, value="select * from assignment where user_id=:user_id and evaluation_id=:eva ")
	AssignmentEntity findAllAssignmentForScoring(@Param("user_id") Long user_id, @Param("eva") Long eva_id);

	@Query(nativeQuery = true, value="select * from assignment where evaluation_id=:eva ")
	List<AssignmentEntity> findByRefScore(@Param("eva") Long eva_id);

}
