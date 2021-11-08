package com.airFrance.offertest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.airFrance.offertest.model.UserModel;

/**
 * this Class represents the DAO implementation for the User Data
 * @author chichaouiomar
 *
 */
@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<UserModel, Long> {
	Optional<UserModel> findByEmail(String email);
}
