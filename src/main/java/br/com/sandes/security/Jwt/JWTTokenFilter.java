package br.com.sandes.security.Jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import br.com.sandes.exceptions.InvalidJWTAuthenticationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class JWTTokenFilter extends GenericFilterBean{
	
	@Autowired
	private JWTTokenProvider tokenProvider;

	public JWTTokenFilter(JWTTokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String token = tokenProvider.resolvToken((HttpServletRequest)request);
		
		try {
			if (token != null && tokenProvider.validateToken(token)) {
				Authentication auth = tokenProvider.getAuthenticaton(token);
				
				if (auth != null) {
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			}
		} catch (InvalidJWTAuthenticationException e) {

			e.printStackTrace();
		}
		
		chain.doFilter(request, response);
		
	}
}
