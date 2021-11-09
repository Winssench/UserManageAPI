package com.airFrance.offertest.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.airFrance.offertest.model.UserModel;
import com.airFrance.offertest.repository.UserRepository;

import lombok.AllArgsConstructor;

/**
 * 
 * @author chichaouiomar
 *
 */
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

	private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
	private final UserRepository userepository;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * @see UserDetailsService#loadUserByUsername(String)
	 * @param String
	 * 
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return userepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
	}

	/**
	 * this Method creates new user Once Valid and persist it to the database
	 * 
	 * @param user
	 * @return String
	 */
	public String signUpUser(UserModel user) {
		boolean userExist = userepository.findByEmail(user.getEmail()).isPresent();
		if (userExist)
			throw new IllegalStateException("email already taken");

		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());

		user.setPassword(encodedPassword);

		userepository.save(user);

		return "it Works";
	}

	/*
	 * this method is same as Signup without the encryption part for testing purpose
	 */
	public void addUser(UserModel user) {
		boolean userExist = userepository.findByEmail(user.getEmail()).isPresent();
		if (userExist)
			throw new IllegalStateException("Email " + user.getEmail() + " taken");

		userepository.save(user);

	}

}
