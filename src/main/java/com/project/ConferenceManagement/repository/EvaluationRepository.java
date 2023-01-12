package com.project.ConferenceManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.ConferenceManagement.entity.EvaluationEntity;
import com.project.ConferenceManagement.entity.UserEntity;

public interface EvaluationRepository extends JpaRepository<EvaluationEntity, Long>{
	
	@Query(nativeQuery = true,value="select * from evaluation where user_id=:user ")
	List<EvaluationEntity> findAllEvaluationOfUsers(@Param("user") UserEntity user );

	
}
