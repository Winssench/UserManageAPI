package com.airFrance.offertest.userdetail;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.airFrance.offertest.model.UserModel;
import com.airFrance.offertest.repository.UserRepository;
import com.airFrance.offertest.service.UserService;

@RestController
@RequestMapping(path = "api/v1/userDetail")
public class UserController {
	
	@Autowired
	UserRepository repository;
	
	private UserService userService;
	
	/*
	 * 
	@GetMapping
	public ResponseEntity<String> getUserByEmail(@RequestParam String email)
	{
		
		Optional<UserModel> user = repository.findByEmail(email);
		
		return ResponseEntity.ok(user.toString());
		
	}
	 */
	
	@GetMapping
	public ResponseEntity<UserModel> getUserByEmail(@RequestParam String email)
	{
		
		//Optional<UserModel> user = repository.findByEmail(email);
		
		//return new ResponseEntity<UserModel>(user, HttpStatus.CREATED);
		
		return repository.findByEmail(email).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.noContent().build());
		//return ResponseEntity.ok(user.toString());
		
	}


}
