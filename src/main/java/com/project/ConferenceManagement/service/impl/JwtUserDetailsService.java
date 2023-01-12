package com.project.ConferenceManagement.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.ConferenceManagement.entity.UserEntity;
import com.project.ConferenceManagement.repository.UserRepository;


@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository UserRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			UserEntity user=new UserEntity();
		if (username!=null) {
			user=findUserByEmail(username);
				if(user!=null) {
				user.setEmail(username+"_"+user.getRole());
				return new User(user.getEmail(), user.getPassword(),
						new ArrayList<>());
				}
				else {
					throw new UsernameNotFoundException("User not found with username: " + username);
				}
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
	
	public UserDetails loadUserByUsernameForAdmin(String username) throws UsernameNotFoundException {
			UserEntity user=new UserEntity();
		if (username!=null) {
			user=findUserByEmail(username);
			if(user.getRole().equals("admin")||user.getRole().equals("Admin")||user.getRole().equals("ADMIN")){
				return new User(user.getEmail(), user.getPassword(),
						new ArrayList<>());
			}
			else {
				return null;
			}
			
		} else {
			throw new UsernameNotFoundException("Admin not found with username: " + username);
		}
	}
	
	public UserEntity findUserByEmail(String username) {
		return UserRepository.findByEmail(username);
	}

}