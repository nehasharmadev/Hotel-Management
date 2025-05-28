package com.example.HotelManagement.dto;

import com.example.HotelManagement.enums.UserRole;

import lombok.Data;

@Data
public class Authenticationresponse {

	private String jwt;
	private long userId; 
	private UserRole userRole;
}
