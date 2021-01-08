package com.micro.oauth.services;


import com.micro.users.commons.models.entity.User;

public interface IUsuarioService {
	
	
	User findByUsername (String username);
	
	void update (User  user, long id);

}
