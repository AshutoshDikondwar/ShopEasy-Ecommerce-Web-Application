package com.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurity {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain myAuthorization(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeHttpRequests() // simply tell spring sec , to authorize all reqs
				.antMatchers("/products/view", "/swagger*/**", "/v*/api-docs/**", "/user/create", "/user/login",
						"/user/authenticate", "/user/update*/**", "/user/delete*/**", "/user*/**", "/user/all")
				.permitAll().anyRequest() // any other remaining end points
				.authenticated() // must be : must be
				.and() // bridge
				.httpBasic(); // support Basic authentication

//		.antMatchers("/products/view", "/swagger*/**", "/v*/api-docs/**", "/user", "/user/login",
//        "/user/authenticate", "/update/**", "/delete/**")

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

}
