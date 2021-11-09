package com.airFrance.offertest.registration;

import javax.validation.Valid;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;


/**
 * This controller enable user sign up
 * @author chichaouiomar
 *
 */
@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {
	
	
	private RegistrationService registrationService;
	
	
	/**
	 * enable regirstration via this method
	 * @param request
	 * @return {@link ResponseEntity}
	 */
	@PostMapping
	public ResponseEntity<String> register(@Valid @RequestBody RegistrationRequest request) {
		
		return registrationService.register(request);
		
	}
}
