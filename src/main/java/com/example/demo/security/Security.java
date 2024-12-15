package com.example.demo.security;

import static org.springframework.security.config.Customizer.withDefaults;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.demo.jwt.JwtTokenFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class Security {
	@Autowired
	private JwtTokenFilter jwtTokenFilter;


	@Bean
	public AuthenticationManager authManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable());
		http.cors(withDefaults());
		http.sessionManagement(ses -> ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.exceptionHandling(ext -> ext.authenticationEntryPoint((request, response, ex) -> {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}).accessDeniedHandler((request, response, ex)->{response.addHeader("access_denied_reason", "not_authorized");response.sendError(403, "Access Denied");}).authenticationEntryPoint((request, response, ex)->{response.addHeader("access_denied_reason", "authentication_required");response.sendError(HttpServletResponse.SC_UNAUTHORIZED);}));
		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
		http.authorizeHttpRequests(auth -> auth
					.requestMatchers("/api/v1/**").permitAll()
					.requestMatchers("/api/productos/listar").hasAnyAuthority("ROLE_ADMIN")
					.requestMatchers("/api/productos/agregar").hasAnyAuthority("ROLE_ADMIN")
					.requestMatchers("/api/productos/images-producto/**").permitAll()
					.requestMatchers("/api/productos/imagenes/{idProducto}/agregar/**").permitAll()
					.requestMatchers("/api/productos/images/**").permitAll()
					.requestMatchers("/api/banner/images/**").permitAll()
					.requestMatchers("/api/popup/images/**").permitAll()
					.requestMatchers("/api/menu/images/**").permitAll()
					.requestMatchers("/api/menuSub/images/**").permitAll()
					.requestMatchers("/api/articulos/images/**").permitAll()
					.requestMatchers("/api/footer/images/**").permitAll()
					.requestMatchers("/api/logos/images/**").permitAll()
					.requestMatchers("/api/pedidos/**").hasAnyAuthority("ROLE_SUPERVISOR","ROLE_ADMIN")
					.anyRequest().authenticated());

		
		return http.build();

	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	  CorsConfiguration configuration = new CorsConfiguration();
	  configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://mus.elvisalcantara.com", "http://elvisalcantara.com"));
	  configuration.setAllowedMethods(Arrays.asList("GET","POST","PATCH", "PUT", "DELETE", "OPTIONS", "HEAD"));
	  configuration.setAllowCredentials(true);
	  configuration.setAllowedHeaders(Arrays.asList("Authorization","Content-Type"));
	  configuration.setExposedHeaders(Arrays.asList("X-Get-Header"));
	  configuration.setMaxAge(3600L);
	  UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	  source.registerCorsConfiguration("/**", configuration);
	  return source;
	}
}
