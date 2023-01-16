package com.project.ConferenceManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.ConferenceManagement.entity.AssignmentEntity;
import com.project.ConferenceManagement.entity.KeyEntity;

@Repository
public interface UserKeyRepository extends JpaRepository<KeyEntity, Long>{

	@Query(nativeQuery = true, value="select * from user_key_match where match_key=:key ")
	List<KeyEntity> findAllKeyByRef(@Param("key") String key);
	
	@Query(nativeQuery = true, value="select count(*) from user_key_match where user_id=:user_id ")
	int findKeyCountByRef(@Param("user_id") Long user_id);


}
