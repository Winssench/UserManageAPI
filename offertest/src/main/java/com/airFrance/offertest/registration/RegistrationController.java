package com.airFrance.offertest.registration;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {
	
	
	private RegistrationService registrationService;
	
	
	@PostMapping
	public ResponseEntity<String> register(@Valid @RequestBody RegistrationRequest request) {
		return registrationService.register(request);
	}
}
