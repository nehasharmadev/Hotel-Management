package com.example.HotelManagement.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
 
@Component
public class JwtUtil {

	 @Value("${jwt.secret}")
	    private String secret;

	    private SecretKey getSignKey() {
	        // encode the secret key using UTF-8 and convert to HMAC-SHA key
	        return Keys.hmacShaKeyFor(secret.getBytes());
	    }
	    
	private String generateToken(Map<String,Object> extraClaims, UserDetails details) {
		return Jwts.builder().setClaims(extraClaims)
				            .setSubject(details.getUsername())
				            .setIssuedAt(new Date(System.currentTimeMillis()))
				            .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24))
				            .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}
	
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		String username = extractUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}
	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				   .setSigningKey(getSignKey())
				   .build()
				   .parseClaimsJws(token).getBody();
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
		
	}
	
	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
}
