package com.example.HotelManagement.Config;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.HotelManagement.serviceAuth.UserService;
import com.example.HotelManagement.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter{
    private final JwtUtil util;
    private final UserService userService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
           final String authheaders = request.getHeader("Authorization");	
           if(StringUtils.isEmpty(authheaders) || !StringUtils.startsWith(authheaders, "Bearer")) {
        	   filterChain.doFilter(request, response);
        	   return;
           }
           final String jwt;
           jwt = authheaders.substring(7);
           final String userEmail;
           userEmail = util.extractUsername(jwt);
           if(StringUtils.isNoneEmpty(userEmail) &&
        		   SecurityContextHolder.getContext().getAuthentication() == null) {
        	   UserDetails userDetails = userService.loadUserByUsername(userEmail);
        	   if(util.isTokenValid(jwt, userDetails)) {
        		   SecurityContext context = SecurityContextHolder.createEmptyContext();
        		   UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
        				   userDetails, null, userDetails.getAuthorities());
        		   authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        		   context.setAuthentication(authToken);
        		   SecurityContextHolder.setContext(context);
        	   }
           }
           filterChain.doFilter(request, response);
		
	}

}
