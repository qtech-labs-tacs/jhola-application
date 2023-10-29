package com.jhola.security.configuration;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jhola.security.dto.UserDTO;
import com.jhola.security.service.CustomUserDetailsService;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {

		try {

			String jwt = getJWTFromRequest(httpServletRequest);

			if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
				
//				Long userId = tokenProvider.getUserIdFromJWT(jwt);
//				
//				UserEntity userDetails = customUserDetailsService.loadUserById(userId);
				String username = tokenProvider.getUsernameFromJWT(jwt);
				
				UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
				
				UserDTO user = modelMapper.map(userDetails, UserDTO.class);

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						user, null, userDetails.getAuthorities());

				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
				
				
				//VERY IMP
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			}

		} catch (Exception ex) {
			logger.error("Could not set user authentication in security context", ex);
		}

		filterChain.doFilter(httpServletRequest, httpServletResponse);

	}

	private String getJWTFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader(SecurityConstants.HEADER_STRING);

		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			return bearerToken.substring(7, bearerToken.length());
		}

		return null;
	}
}
