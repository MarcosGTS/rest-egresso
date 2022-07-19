package com.egresso.ufma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableWebMvc
public class UfmaApplication implements WebMvcConfigurer {

	// @Override
	// public void addCorsMappings(CorsRegistry cors) {
	// 	cors.addMapping("/**")
	// 		.allowedMethods("GET", "PUT", "DELETE", "POST", "OPTIONS");
	// }

	@Bean
	public PasswordEncoder bCryptPasswordEncoder() {
    	return new BCryptPasswordEncoder();
  	}

	public static void main(String[] args) {
		SpringApplication.run(UfmaApplication.class, args);
	}

}
