package com.airFrance.offertest.registration;

import java.time.LocalDate;

import com.airFrance.offertest.model.Gender;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


/**
 * this represents the registration request object coming from the user 
 * @author chichaouiomar
 *
 */

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
	
	private final String firstName;
	private final String lastName;
	private final Gender gender;
	private final String email;
	private final String password;
	private final String country;
	private final LocalDate birthday;
	private final String phoneNumber;
	

}
