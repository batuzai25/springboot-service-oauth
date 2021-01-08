package com.micro.oauth.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;

import com.micro.oauth.services.IUsuarioService;
import com.micro.users.commons.models.entity.User;

@Component
public class InfAddToken implements TokenEnhancer {
	
	@Autowired
	private IUsuarioService userService;// para obtener inf adicional de usuario

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> info = new HashMap<>();
		User user = userService.findByUsername(authentication.getName());
		
		info.put("nombre", user.getNombre());// inf adicional de usuarios
		info.put("apellido", user.getApellido());
		info.put("email", user.getEmail());
		
		//accessToken.getAdditionalInformation().putAll(info); otra forma probar
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		
		
		return accessToken;
	}

}
