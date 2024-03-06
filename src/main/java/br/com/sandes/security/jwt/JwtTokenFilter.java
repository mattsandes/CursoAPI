package br.com.sandes.security.jwt;

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

public class JwtTokenFilter extends GenericFilterBean{
	
	@Autowired
	private JwtTokenProvider tokenProvider;

	public JwtTokenFilter(JwtTokenProvider tokenProvider) {
		super();
		this.tokenProvider = tokenProvider;
	}

	@Override
	//esse metodo vai pegar a permissao que esta no token e se essa autenticação estiver no token, ele seta no contexto do spring;
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String token = tokenProvider.resolveToken ((HttpServletRequest) request);
		
		try {
			if (token != null && tokenProvider.validateToken(token)) {
				Authentication auth = tokenProvider.getAuthentication(token);
				
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
