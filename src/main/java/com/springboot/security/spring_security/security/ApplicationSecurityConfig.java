package com.springboot.security.spring_security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter{

	private  final PasswordEncoder bPasswordEncrypt;
	
	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder bPasswordEncrypt) {
		this.bPasswordEncrypt = bPasswordEncrypt;
	}

	
	
	//This method implementation have authority without using @PreAuthority annotation in controller level method and EnableGlobalMethodSecurity
	/*@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/","/index","/css/*","/js/*").permitAll()
		.antMatchers("/api/**").hasRole(ApplicationRoles.ADMIN.name())
				  .antMatchers("/management/api/**").hasAnyRole(ApplicationRoles.ADMIN.name(), ApplicationRoles.ADMINTRAINEE.name())
				  .antMatchers(HttpMethod.POST,"/management/api/v1/student-center/**").hasAuthority(ApplicationPermission.COURSE_WRITE.getPermission())
				  .antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(ApplicationPermission.COURSE_WRITE.getPermission())
				  .antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(ApplicationPermission.COURSE_WRITE.getPermission())
				 
		.anyRequest()
		.authenticated()
		.and()
		.httpBasic();
	}*/
	
	
	//This method implementation have authority using @PreAuthority annotation in controller level method and EnableGlobalMethodSecurity

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/","/index","/css/*","/js/*").permitAll()
		.antMatchers("/api/**").hasRole(ApplicationRoles.ADMIN.name())
		.anyRequest()
		.authenticated()
		.and()
		.httpBasic();
	}

	@Override
	@Bean
	protected UserDetailsService userDetailsService(){
		UserDetails manjeet= User.builder()
		             .username("manjeet")
		             .password(bPasswordEncrypt.encode("kumar"))
		            // .roles(ApplicationRoles.ADMIN.name())
		             .authorities(ApplicationRoles.STUDENT.getGrantedAuthorities())
		             .build();
		
	   UserDetails anish=User.builder()
			                 .username("anish")
			                 .password(bPasswordEncrypt.encode("anish@123"))
			                // .roles(ApplicationRoles.STUDENT.name())
			                .authorities(ApplicationRoles.ADMIN.getGrantedAuthorities())
			                 .build();
	   
	   UserDetails tom=User.builder()
               .username("tom")
               .password(bPasswordEncrypt.encode("tom@321"))
              // .roles(ApplicationRoles.ADMINTRAINEE.name())
               .authorities(ApplicationRoles.ADMINTRAINEE.getGrantedAuthorities())
               .build();
		
		return new InMemoryUserDetailsManager(manjeet,anish,tom);
	}

	
	
}
