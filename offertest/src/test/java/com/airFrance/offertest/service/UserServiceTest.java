package com.airFrance.offertest.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.airFrance.offertest.model.AppUserRole;
import com.airFrance.offertest.model.Gender;
import com.airFrance.offertest.model.UserModel;
import com.airFrance.offertest.repository.UserRepository;

/**
 * This class represents the unit Test for the UserService
 * @author chichaouiomar
 *
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepo;
	private UserService underTest;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@BeforeEach
	void setUp() {
		underTest = new UserService(userRepo, bCryptPasswordEncoder);
	}

	@Test
	void addUser() {
		// given
		String email = "pierre@gmail.com";
		String password = null;
		UserModel user = new UserModel(email, "pass", "Pierre", "Dupont", AppUserRole.USER, Gender.MALE, "0687345465",
				"France", LocalDate.of(2000, 9, 12));

		user.setPassword(password);
		// when
		underTest.addUser(user);
		// then
		ArgumentCaptor<UserModel> userArgumentCaptor = ArgumentCaptor.forClass(UserModel.class);
		verify(userRepo).save(userArgumentCaptor.capture());
		UserModel capturedUser = userArgumentCaptor.getValue();

		assertThat(capturedUser).isEqualTo(user);

	}

	@Test
	void willThrowWnehEmailIsTaken() {
		// given
		String email = "pierre@gmail.com";
		UserModel user = new UserModel(email, "pass", "Pierre", "Dupont", AppUserRole.USER, Gender.MALE, "0687345465",
				"France", LocalDate.of(2000, 9, 12));
		// given
		given(userRepo.findByEmail(user.getEmail())).willReturn(Optional.of(user));

		// when
		// then
		assertThatThrownBy(() -> underTest.addUser(user)).isInstanceOf(IllegalStateException.class)
				.hasMessageContaining("Email " + user.getEmail() + " taken");

		verify(userRepo, never()).save(any());

	}

}
