package com.springboot.security.spring_security.security;

import java.util.concurrent.TimeUnit;

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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter{

	private  final PasswordEncoder bPasswordEncrypt;
	
	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder bPasswordEncrypt) {
		this.bPasswordEncrypt = bPasswordEncrypt;
	}

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
		.formLogin()
			.loginPage("/login").permitAll()
			.defaultSuccessUrl("/courses", true)
			.passwordParameter("password")
			.usernameParameter("username")
		.and()
		.rememberMe()
			  .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(10))
			  .key("idontknowwhatisthis")
			  .rememberMeParameter("remember-me")
		.and()
		.logout()
			.logoutUrl("/logout")
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
			.deleteCookies("JSESSIONID","remember-me")
			.clearAuthentication(true)
			.invalidateHttpSession(true)
			.logoutSuccessUrl("/login");
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
