package com.bridgelabz.user.repository;

import java.util.Optional;

import com.bridgelabz.user.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRegistrationRepository extends JpaRepository<UserModel,Long> {

    Optional<UserModel> findByEmailId(String emailId);

    Optional<UserModel> findByemailIdAndPassword(String emailId, String password);

    Optional<UserModel> findById(Long Id);

}
