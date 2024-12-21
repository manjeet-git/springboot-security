package com.springboot.security.spring_security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class StudentApplicationUserService implements UserDetailsService{

	private  StudentAppUserDao appUserDao;
	
	
	@Autowired
	public StudentApplicationUserService(StudentAppUserDao appUserDao) {
		this.appUserDao = appUserDao;
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return appUserDao.selectStudentApplicationUserByUsername(username)
				      .orElseThrow(()-> new UsernameNotFoundException(String.format("User details not found for username : %s",username)));
		
	}

}
