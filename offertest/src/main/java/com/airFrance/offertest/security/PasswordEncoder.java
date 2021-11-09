package com.airFrance.offertest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * THis class represents the Password Encoder for the authentification
 * @author chichaouiomar
 *
 */
@Configuration
public class PasswordEncoder {
	
	/**
	 * 
	 * @return {@link BCryptPasswordEncoder}
	 */
	@Bean
	public BCryptPasswordEncoder bcryptPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
}
