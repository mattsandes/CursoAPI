package br.com.sandes.security.Jwt;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.sandes.data.vo.v1.security.TokenVO;
import br.com.sandes.exceptions.InvalidJWTAuthenticationException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JWTTokenProvider {

	@Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";
	
	@Value("${security.jwt.token.expire-length:3600000}")
	private long validityInMilliseconds = 3600000; //validade de uma hora
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	Algorithm algorithm = null;
	
	/*PostConstruct:
	 * Quando o spring inicia o contexto, ele genrencia tudo que esta anotado como bean e injeta suas dependencias.
	 * Apos isso, ele chama os metodos anotados como postconstruct;*/
	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes()); //encripta o valor da secret key e joga dennovo para o valor da variavel;
		
		algorithm = Algorithm.HMAC256(secretKey.getBytes());
	}
	
	//criando a inicialização do token
	public TokenVO createAccessToken(String username, List<String> roles) {
		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds); //vai pegar a hora atual e setar com mais uma hora;
		
		var accessToken = getAccessToken(username, roles, now, validity);
		var refreshToken = getRefreshToken(username, roles, now);
		return new TokenVO(username, true, now, validity, accessToken, refreshToken);
	}
	
	private String getAccessToken(String username, List<String> roles, Date now, Date validity) {
		String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
		
		return JWT.create()
				.withClaim("roles", roles)
				.withIssuedAt(now)
				.withExpiresAt(validity)
				.withSubject(username)
				.withIssuer(issuerUrl)
				.sign(algorithm)
				.strip();
	}
	
	private String getRefreshToken(String username, List<String> roles, Date now) {
		var validtyRefreshToken = new Date(now.getTime() + (validityInMilliseconds * 3));
		
		return JWT.create()
				.withClaim("roles", roles)
				.withIssuedAt(now)
				.withExpiresAt(validtyRefreshToken)
				.withSubject(username)
				.sign(algorithm)
				.strip();
	}
	
	public Authentication getAuthenticaton(String token) {
		DecodedJWT decodedJWT = decodedToken(token);
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(decodedJWT.getSubject());
		
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	private DecodedJWT decodedToken(String token) {
		var alg = Algorithm.HMAC256(secretKey.getBytes());
		
		JWTVerifier verifier = JWT.require(alg).build();
		DecodedJWT decodedJWT = verifier.verify(token);
		
		return decodedJWT;
	}
	
	public String resolvToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		
		if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring("Bearer ".length());
		}
		
		return null;
	}
	
	public boolean validateToken(String token) throws InvalidJWTAuthenticationException {
		DecodedJWT decodedJWT = decodedToken(token);
		
		try {
			if(decodedJWT.getExpiresAt().before(new Date())) {
				return false;
			}
			
			return true;
		} catch (Exception e) {
			throw new InvalidJWTAuthenticationException("Expired or invalid JWT token!");
		}
	}
}
