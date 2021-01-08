package com.micro.oauth.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.micro.oauth.client.UsuarioFeignClient;
import com.micro.oauth.services.IUsuarioService;
import com.micro.users.commons.models.entity.User;

import brave.Tracer;
import feign.FeignException;

@Service
public class UserService implements IUsuarioService, UserDetailsService {
	private static Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UsuarioFeignClient userFeign;
	
	@Autowired
	
	private Tracer tracer;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		try {

			User userC = userFeign.findByUsername(username);

			List<GrantedAuthority> authorities = userC.getRoles().stream()
					.map(role -> new SimpleGrantedAuthority(role.getNombre()))
					.peek(autority -> log.info("role:" + autority.getAuthority())).collect(Collectors.toList());

			log.info("User name auntenticado: " + userC.getUsername());
			
			return new org.springframework.security.core.userdetails.User(userC.getUsername(), userC.getPassword(),
					userC.getEnable(), true, true, true, authorities);

		} catch (FeignException e) {
			StringBuilder messagesError = new StringBuilder("User not found ").append(username);
			tracer.currentSpan().tag("error.message",messagesError.append(": ").append(e.getMessage()).toString());
			log.error(messagesError.toString());
			
			throw new UsernameNotFoundException(messagesError.toString());
		}

		
	}

	// obtener informacion adicional de usuario

	@Override
	public User findByUsername(String username) {
		User userC = userFeign.findByUsername(username);
		return userC;
	}

	@Override
	public void update(User user, long id) {
		userFeign.update(user, id);

	}

}
