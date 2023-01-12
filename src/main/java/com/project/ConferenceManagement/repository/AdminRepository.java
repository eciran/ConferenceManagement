package com.project.ConferenceManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ConferenceManagement.entity.UserEntity;

public interface AdminRepository extends JpaRepository<UserEntity, Long> {

}
