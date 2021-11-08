package com.airFrance.offertest.registration;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.airFrance.offertest.model.AppUserRole;
import com.airFrance.offertest.model.UserModel;
import com.airFrance.offertest.service.UserService;

import lombok.AllArgsConstructor;

/**
 * THis class implements the User Service and its functionalities
 * 
 * @author chichaouiomar
 *
 */
@Service
@AllArgsConstructor
public class RegistrationService {

	private final UserService userService;

	public ResponseEntity<String> register(RegistrationRequest request) {

		boolean isFrance = request.getCountry().equals("France");

		LocalDate today = LocalDate.now();
		LocalDate birthday = request.getBirthday();
		Period p = Period.between(birthday, today);

		boolean isAdult = p.getYears() >= 18;

		if (!(isAdult && isFrance)) {
			throw new IllegalStateException("Only adult French residents are allowed to create an account!");
		}

		String msg = userService.signUpUser(new UserModel(request.getEmail(), request.getPassword(),
				request.getFirstName(), request.getLastName(), AppUserRole.USER, request.getGender(),
				request.getPhoneNumber(), request.getCountry(), request.getBirthday()

		));

		return ResponseEntity.status(HttpStatus.OK).body(msg);
	}

}
