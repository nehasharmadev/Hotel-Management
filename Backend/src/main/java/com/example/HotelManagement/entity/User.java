package com.example.HotelManagement.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.HotelManagement.dto.Userdto;
import com.example.HotelManagement.enums.UserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User implements UserDetails{

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long id;
private String name;
private String email;
private String password;
private UserRole userRole;
@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
	// TODO Auto-generated method stub
	return List.of(new SimpleGrantedAuthority(userRole.name()));
}
@Override
public String getUsername() {
	// TODO Auto-generated method stub
	return email;
}
public Userdto getUserdto() {
	// TODO Auto-generated method stub
	Userdto dto = new Userdto();
	dto.setId(id);
	dto.setEmail(email);
	dto.setName(name);
	dto.setUserRole(userRole);
	return dto;
}

}
