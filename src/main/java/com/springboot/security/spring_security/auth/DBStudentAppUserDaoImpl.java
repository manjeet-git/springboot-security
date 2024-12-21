package com.springboot.security.spring_security.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.springboot.security.spring_security.security.ApplicationRoles;

@Repository
public class DBStudentAppUserDaoImpl implements StudentAppUserDao{

	private PasswordEncoder password;
	
	@Autowired
	public DBStudentAppUserDaoImpl(PasswordEncoder password) {
		this.password = password;
	}


	@Override
	public Optional<StudentApplicationUser> selectStudentApplicationUserByUsername(String username) {
		
		return getStudentApplicationUsers().stream().filter(user->username.equals(user.getUsername())).findFirst();
	}
	
	
	public List<StudentApplicationUser> getStudentApplicationUsers(){
		List<StudentApplicationUser> users=Lists.newArrayList
				(new StudentApplicationUser(ApplicationRoles.STUDENT.getGrantedAuthorities(),
											password.encode("kumar"),
											"manjeet",
											true,
											true,
											true,
											true),
				new StudentApplicationUser(ApplicationRoles.ADMIN.getGrantedAuthorities(),
											password.encode("anish@123"),
											"anish",
											true,
											true,
											true,
											true),
				new StudentApplicationUser(ApplicationRoles.ADMINTRAINEE.getGrantedAuthorities(),
											password.encode("tom@321"),
											"tom",
											true,
											true,
											true,
											true));
		
		return users;
		
		
	}

}
