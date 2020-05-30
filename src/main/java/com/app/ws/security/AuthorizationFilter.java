package com.app.ws.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	public AuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, 
									HttpServletResponse res, 
									FilterChain chain) throws IOException, ServletException {
		
		String header = req.getHeader(SecurityConstants.HEADER_STRING);
		
		if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}
		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
		String token = req.getHeader(SecurityConstants.HEADER_STRING);
		if (token != null) {
			token = token.replace(SecurityConstants.TOKEN_PREFIX, "");
			String user = Jwts.parser().setSigningKey(SecurityConstants.getToken()).parseClaimsJws(token).getBody()
					.getSubject();
			Claims body = Jwts.parser().setSigningKey(SecurityConstants.getToken()).parseClaimsJws(token).getBody();
			List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities");
			Set<SimpleGrantedAuthority> simpleGrantedAuthorities =
					authorities.stream().map(m -> new SimpleGrantedAuthority(m.get("authority")))
					.collect(Collectors.toSet());
			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null,simpleGrantedAuthorities);
			}
			return null;
		}
		return null;
	}

}
