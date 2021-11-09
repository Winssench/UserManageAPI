
package com.airFrance.offertest.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents the Model for the User
 * 
 * @author chichaouiomar
 * @version 1.0
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER_TBL")
public class UserModel implements UserDetails {
	@Id
	@SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
	private Long uid;

	@NotEmpty
	@Email
	private String email;

	@NotEmpty
	private String password;

	@NotEmpty
	@Size(min = 2, message = "user name should have at least 2 characters")
	private String firstName;

	@NotEmpty
	@Size(min = 2, message = "user lastname should have at least 2 characters")
	private String lastName;

	@Enumerated(EnumType.STRING)
	private AppUserRole appUserRole;

	@Past
	private LocalDate birthday;
	@NotEmpty
	private String country;

	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	/**
	 * @see #getAuthorities()
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRole.name());
		return Collections.singletonList(authority);
	}

	/**
	 * @see UserModel#getPassword()
	 */
	@Override
	public String getPassword() {
		return this.password;
	}

	/**
	 * @see UserModel#isAccountNonExpired()
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * @see #isAccountNonLocked()
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * @see #isCredentialsNonExpired()
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * later if you want to enable user for example if we activate 
	 * email verification later
	 * 
	 * @see #isEnabled()
	 */
	@Override
	public boolean isEnabled() {

		return true;
	}

	/**
	 * 
	 * @see #getUsername()
	 */
	@Override
	public String getUsername() {
		return this.email;
	}

	/**
	 * This instantiate a User from the data we get from the 
	 * user 
	 * @param email
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param appUserRole
	 * @param gender
	 * @param phoneNumber
	 * @param country
	 * @param birthday
	 */
	public UserModel(String email, String password, String firstName, String lastName, AppUserRole appUserRole,
			Gender gender, String phoneNumber, String country, LocalDate birthday) {
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.country = country;
		this.gender = gender;
		this.phoneNumber = phoneNumber;
		this.appUserRole = appUserRole;
	}

}
