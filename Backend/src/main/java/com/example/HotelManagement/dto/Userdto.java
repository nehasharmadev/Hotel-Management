package com.example.HotelManagement.dto;

import com.example.HotelManagement.enums.UserRole;

import lombok.Data;

@Data
public class Userdto {

	private Long id;
	private String email;
	private UserRole userRole;
	private String name;
}
