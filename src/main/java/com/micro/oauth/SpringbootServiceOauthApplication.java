package com.micro.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class SpringbootServiceOauthApplication  implements CommandLineRunner{

	@Autowired
	private  PasswordEncoder  passwordEncoder;
	private static Logger log = LoggerFactory.getLogger(SpringbootServiceOauthApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootServiceOauthApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String password ="12345";
		
		for (int i = 0; i < 4; i++) {
			log.info("Password de prueba: "+passwordEncoder.encode(password));
		}
		
		
		
	}

}
