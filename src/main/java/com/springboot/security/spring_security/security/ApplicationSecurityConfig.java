package com.springboot.security.spring_security.security;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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

import com.springboot.security.spring_security.auth.StudentApplicationUser;
import com.springboot.security.spring_security.auth.StudentApplicationUserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter{

	private final  PasswordEncoder bPasswordEncrypt;
	private final  UserDetailsService userDetailsService;
	
	
	
	
	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder bPasswordEncrypt,
			UserDetailsService userDetailsService) {
		this.bPasswordEncrypt = bPasswordEncrypt;
		this.userDetailsService = userDetailsService;
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
			.clearAuthentication(true)
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID","remember-me")
			.logoutSuccessUrl("/login");  
	}

	
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(getAuthenticationProvider());
	}

	@Bean
    public DaoAuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		provider.setPasswordEncoder(bPasswordEncrypt);
		provider.setUserDetailsService(userDetailsService);
		return provider;
}
	
	
}
