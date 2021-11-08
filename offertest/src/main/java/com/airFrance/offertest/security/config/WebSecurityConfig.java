package com.airFrance.offertest.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.airFrance.offertest.service.UserService;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserService userService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// http.csrf().disable().authorizeRequests().antMatchers("/api/v*/registration/**")
		
		//http.authorizeRequests().antMatchers("/api/v*/registration/**").permitAll().and().authorizeRequests()
			//	.antMatchers("/console/**").permitAll();
		
		/*
		http.authorizeRequests().antMatchers("/").permitAll().and().authorizeRequests()
		.antMatchers("/console/**").permitAll();
		http.csrf().disable();
		*/
		
		//http.authorizeRequests().antMatchers("/console/**").permitAll()
		//.and().antMatchers("/api/v*/registration/**").permitAll().anyRequest().authenticated().and().formLogin();
		//http.csrf().disable().authorizeRequests().antMatchers("/api/v*/registration/**").permitAll().anyRequest().authenticated()
		//.and().formLogin();
		
		
		http.authorizeRequests()
			.antMatchers("/h2-console/**").permitAll()
			.antMatchers("/api/v*/registration/**").permitAll()
			.anyRequest().authenticated()
			
			 .and()
             .exceptionHandling()
             .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            	
             //.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))

			
			 .and()
			 .formLogin()
             .successHandler((req, res, auth) -> res.setStatus(HttpStatus.NO_CONTENT.value()))
             .failureHandler(new SimpleUrlAuthenticationFailureHandler())
             
             .and()
             .logout()
             .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.NO_CONTENT));
             
             
             
			
		
		
		http.csrf().disable();
			
		http.headers().frameOptions().disable();
		
			

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(bCryptPasswordEncoder);
		provider.setUserDetailsService(userService);
		return provider;
	}
}
