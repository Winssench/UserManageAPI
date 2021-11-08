package com.airFrance.offertest.integration;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.airFrance.offertest.model.AppUserRole;
import com.airFrance.offertest.model.Gender;
import com.airFrance.offertest.model.UserModel;
import com.airFrance.offertest.repository.UserRepository;
import com.airFrance.offertest.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

import static org.mockito.Mockito.verify;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.annotation.DirtiesContext.ClassMode;



import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;


import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.springframework.boot.test.context.SpringBootTest.*;

@SpringBootTest(
		 webEnvironment = WebEnvironment.RANDOM_PORT,
			  properties = {
			  "spring.security.user.name=omar",
			  "spring.security.user.password=pass",
			  
			}
)
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class UserIt {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepo;

	// @Autowired
	// private UserService userService;
	  @LocalServerPort
	  private Integer port;

	@Autowired
	private ObjectMapper objectMapper;

	// @Autowired
	// private TestRestTemplate template;

	@BeforeEach
	void setUp() {
		RestAssuredMockMvc.mockMvc(mockMvc);
	}

	@Test
	void canRegisternNewUser() throws Exception {
		// given
		String email = "pierre@gmail.com";
		String password = null;
		UserModel user = new UserModel(email, "pass", "Pierre", "Dupont", AppUserRole.USER, Gender.MALE, "0687345465",
				"France", LocalDate.of(2000, 9, 12));
		// when
		ResultActions resultActions = mockMvc.perform(post("/api/v1/registration")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user))

		);

		// then
		resultActions.andExpect(status().isOk());
		List<UserModel> users = userRepo.findAll();

		boolean exist = users.stream().anyMatch(o -> o.getEmail().equals(user.getEmail()));

		assertTrue(exist);

	}

	@Test
	void shouldDisableUserDetailDisplauWithoutAuthentication() {
		String email = "pierre@gmail.com";
		String password = "pass";
		UserModel user = new UserModel(email, "pass", "Pierre", "Dupont", AppUserRole.USER, Gender.MALE, "0687345465",
				"France", LocalDate.of(2000, 9, 12));
		user.setPassword(password);
		// first we persist the user in the memory database
		userRepo.save(user);
		RestAssuredMockMvc
		.given()
		.auth().none()
		.param("email", email).when().get("/userDetail").then()
		.statusCode(401);
	}
	
	@Test
	void getUserDetailOnceAuthenticated() {
		String email = "pierre@gmail.com";
		String password = "pass";
		UserModel user = new UserModel(email, "pass", "Pierre", "Dupont", AppUserRole.USER, Gender.MALE, "0687345465",
				"France", LocalDate.of(2000, 9, 12));
		user.setPassword(password);
		// first we persist the user in the memory database
		userRepo.save(user);
		//RestAssuredMockMvc
		RestAssured
		.given()
		.filter(new RequestLoggingFilter())
		.auth()
		.basic("omar", "pass")
		//.auth().with(user("omar"))
		//.auth().with(SecurityMockMvcRequestPostProcessors.user("duke"))
		//.auth().with(SecurityMockMvcRequestPostProcessors.user("omar"))
		.contentType("application/json")
		.param("email", email).when()
		//"http://localhost:" + port + "/api/books"
		.get("http://localhost:" + port +"/userDetail").then()
		.statusCode(201);
		
	}

}
