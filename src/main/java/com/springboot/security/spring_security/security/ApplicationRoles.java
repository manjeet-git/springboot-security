package com.springboot.security.spring_security.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;

public enum ApplicationRoles {
	STUDENT(Sets.newHashSet()),
	
	ADMIN(Sets.newHashSet(ApplicationPermission.STUDENT_READ,
			              ApplicationPermission.STUDENT_WRITE,
			              ApplicationPermission.COURSE_READ,
			              ApplicationPermission.COURSE_WRITE)),
	
	ADMINTRAINEE(Sets.newHashSet(ApplicationPermission.STUDENT_READ,
			                     ApplicationPermission.COURSE_READ));
	
	private final Set<ApplicationPermission> permissions;

	private ApplicationRoles(Set<ApplicationPermission> permissions) {
		this.permissions = permissions;
	}
	
	public Set<ApplicationPermission> getPermissions() {
		return permissions;
	}

	public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
		Set<SimpleGrantedAuthority> permissions=getPermissions().stream()
		                .map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toSet());
		permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		return permissions;
	}
}
