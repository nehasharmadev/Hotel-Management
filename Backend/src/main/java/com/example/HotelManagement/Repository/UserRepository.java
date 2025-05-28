package com.example.HotelManagement.Repository;

import org.springframework.stereotype.Repository;

import com.example.HotelManagement.entity.User;
import com.example.HotelManagement.enums.UserRole;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface UserRepository extends JpaRepository<User,Long>{

	Optional<User> findFirstByEmail(String email);
	Optional<User> findByUserRole(UserRole userRole);
}
