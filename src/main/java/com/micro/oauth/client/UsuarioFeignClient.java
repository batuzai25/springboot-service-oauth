package com.micro.oauth.client;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.micro.users.commons.models.entity.User;

@FeignClient(name = "service-users")
public interface UsuarioFeignClient {
	
	@GetMapping("/user/search/buscarPorNombre")
	User findByUsername(@RequestParam String username);
	
	
	@PutMapping("/user/{id}")
    void update (@RequestBody User  user, @PathVariable long id);
}
