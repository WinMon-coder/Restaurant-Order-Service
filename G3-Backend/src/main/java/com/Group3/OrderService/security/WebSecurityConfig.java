package com.Group3.OrderService.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
        .authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers("/**").permitAll()
        .antMatchers("/admin/**").hasRole("ADMIN")
        .antMatchers("/user/**").hasAnyRole("ADMIN","WAITER")
        .and()
        .formLogin()
        .loginPage("/")
        .defaultSuccessUrl("/")
        .and()
		.logout().invalidateHttpSession(true)
		.logoutSuccessUrl("/")
		.and()
        .rememberMe()
        .and()
        .csrf()
        .disable();
	}
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.debug(false).ignoring()
				.antMatchers("/images/**", "/js/**", "/css/**");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth.userDetailsService(userDetailsService)
         .passwordEncoder(bCryptPasswordEncoder);
	}
}
