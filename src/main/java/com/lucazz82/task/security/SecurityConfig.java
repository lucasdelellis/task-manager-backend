package com.lucazz82.task.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.lucazz82.task.enums.Roles;
import com.lucazz82.task.filters.CustomAuthenticationFilter;
import com.lucazz82.task.filters.CustomAuthorizationFilter;
import com.lucazz82.task.filters.ExceptionHandlerFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService _userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder _bCryptPasswordEncorder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this._userDetailsService).passwordEncoder(this._bCryptPasswordEncorder);		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Configuration of login url
		CustomAuthenticationFilter filter = new CustomAuthenticationFilter(this.authenticationManagerBean());
		filter.setFilterProcessesUrl("/auth/login");
		
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers("/auth/**").permitAll(); // Register
		http.authorizeRequests().antMatchers("/user/**").hasRole(Roles.USER.name());
		http.authorizeRequests().antMatchers("/admin/**").hasRole(Roles.ADMIN.name());
		http.authorizeRequests().anyRequest().authenticated();
		http.addFilter(filter);
		http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(new ExceptionHandlerFilter(), CustomAuthorizationFilter.class);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	

}
