package com.airFrance.offertest.userdetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.airFrance.offertest.model.UserModel;
import com.airFrance.offertest.repository.UserRepository;

/**
 * This represent the second Service providing the user Detail 
 * 
 * @author chichaouiomar
 * @version 1.0
 */
@RestController
@RequestMapping(path = "api/v1/userDetail")
public class UserController {

	@Autowired
	UserRepository repository;

	/**
	 * this method return the user information once the user provide the 
	 * @param email
	 * @return {@link ResponseEntity}
	 */
	@GetMapping
	public ResponseEntity<UserModel> getUserByEmail(@RequestParam String email) {

		return repository.findByEmail(email).map(ResponseEntity::ok)
				//.orElseGet(() -> ResponseEntity.noContent().build());
				.orElseGet(() -> ResponseEntity.notFound().build());

	}

}
