package com.airFrance.offertest.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.airFrance.offertest.model.AppUserRole;
import com.airFrance.offertest.model.Gender;
import com.airFrance.offertest.model.UserModel;

/**
 * THis class represents the unit Test for the DAO/ the Repository
 * @author chichaouiomar
 *
 */
@DataJpaTest
class UserRepositoryTest {

	@Autowired
	private UserRepository underTest;

	@AfterEach
	void tearDown() {
		underTest.deleteAll();
	}

	@Test
	void itSHouldCheckIfStudentExistsbyEmail() {
		// given
		String email = "pierre@gmail.com";
		UserModel user = new UserModel(email, "pass", "Pierre", "Dupont", AppUserRole.USER, Gender.MALE, "0687345465",
				"France", LocalDate.of(2000, 9, 12));

		underTest.save(user);

		// when
		boolean expected = underTest.findByEmail(email).isPresent();

		// then
		assertThat(expected).isTrue();

	}

	@Test
	void itSHouldCheckIfStudentEmailDoesntExist() {
		// given
		String email = "pierre@gmail.com";

		// when
		boolean expected = underTest.findByEmail(email).isPresent();

		// then
		assertThat(expected).isFalse();

	}

}
