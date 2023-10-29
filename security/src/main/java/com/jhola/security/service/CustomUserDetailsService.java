package com.jhola.security.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhola.security.model.AuthorityEntity;
import com.jhola.security.model.RoleEntity;
import com.jhola.security.model.UserEntity;
import com.jhola.security.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByUsername(username);
		if (user == null) {
			new UsernameNotFoundException("User not found");
		}

		Collection<GrantedAuthority> authorities = new ArrayList<>();
		Collection<RoleEntity> roles = user.getRoles();

		roles.forEach((role) -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));

			Collection<AuthorityEntity> authorityEntities = role.getAuthorities();
			authorityEntities.forEach((authorityEntity) -> {
				authorities.add(new SimpleGrantedAuthority(authorityEntity.getName()));
			});
		});

		return new User(user.getUsername(), user.getPassword(), true, true, true, true, authorities);

	}

	@Transactional
	public UserEntity loadUserById(Long id) {
		UserEntity user = userRepository.getById(id);
		if (user == null)
			new UsernameNotFoundException("User not found");
		return user;

	}
}
