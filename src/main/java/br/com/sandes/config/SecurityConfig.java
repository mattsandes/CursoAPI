package br.com.sandes.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.sandes.security.jwt.JwtTokenFilter;
import br.com.sandes.security.jwt.JwtTokenProvider;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Autowired
	private JwtTokenProvider tokenProvider;
	
	//esse meotodo vai dizer ao spring como encriptar a senha para comparar com a senha que esta no banco;
	@Bean
	PasswordEncoder passwordEncoder() {
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		
		Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = 
				new Pbkdf2PasswordEncoder("",
						8,
						185000,
						SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
		
		encoders.put("pbkdf2", pbkdf2PasswordEncoder);
		
		DelegatingPasswordEncoder passwordEncoder = 
				new DelegatingPasswordEncoder("pbkdf2", encoders);
		
		passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2PasswordEncoder);
		
		return passwordEncoder;
	}
	
	@Bean
	AuthenticationManager authenticationManagerBean(
			AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	SecurityFilterChain secutiryFilterChain(HttpSecurity http) throws Exception {
		JwtTokenFilter customFilter = new JwtTokenFilter(tokenProvider);
		
		http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http
				.httpBasic(basic -> basic.disable())
				.csrf(AbstractHttpConfigurer::disable)
				.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(
					authorizeHttpRequests -> authorizeHttpRequests
						.requestMatchers(
								"/auth/**",
								"/swagger-ui/**",
								"/v3/api-docs/**"
						).permitAll()
						.requestMatchers("/api/**").authenticated()
						.requestMatchers("/users").denyAll()
			)
			.cors(cors -> cors.disable())
			.build();
	}
	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
}
