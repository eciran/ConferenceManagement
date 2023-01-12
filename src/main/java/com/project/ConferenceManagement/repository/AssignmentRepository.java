package com.project.ConferenceManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.ConferenceManagement.entity.AssignmentEntity;

@Repository
public interface AssignmentRepository extends JpaRepository<AssignmentEntity, Long > {

	@Query(nativeQuery = true, value="select * from assignment where evaluation_id =2 ")
	List<AssignmentEntity> findAllRefForEvaluation();
}
