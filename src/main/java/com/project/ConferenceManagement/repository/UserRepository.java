package com.project.ConferenceManagement.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.ConferenceManagement.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{

	UserEntity findByEmail(String email);
	
	@Transactional
	@Modifying
	@Query(value = "update user set first_name = ? and last_name = ? and role = ? where email= ?", nativeQuery = true)
	void userUpdateByAdmin(String firstName,String lastName,String role,String email);

	@Query(value = "select count(id) from UserEntity where registerDate > :registerDate")
	String getuserLastRegister(@Param("registerDate") Date registerDate);
	
	@Query(value="select * from user where role='REFEREE'", nativeQuery = true)
	List<UserEntity> getRefList();

	@Query(value="select * from user where role='AUTHOR'", nativeQuery = true)
	List<UserEntity> getAuthorList();
	

}
