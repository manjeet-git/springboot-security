package com.springboot.security.spring_security.auth;

import java.util.Optional;

public interface StudentAppUserDao {
	public Optional<StudentApplicationUser> selectStudentApplicationUserByUsername(String username);
}
