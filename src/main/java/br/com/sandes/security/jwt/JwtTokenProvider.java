package br.com.sandes.security.jwt;

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
//Essa classe vai ficar responsavel por criar o token da requisição;
public class JwtTokenProvider {

	//setando os valores senha do token e tempo valido para o token;
	//casoo nao haja valores no application yaml, o teken tera esses valores setados;
	@Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";
	
	@Value("${security.jwt.token.expire-lenght:3600000}")
	private long validityInMilliseconds = 3600000;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	Algorithm algorithm = null;
	
	//definindo um postconstruct
	@PostConstruct
	protected void init() {
		//ele pegar o valor que setamos na secret key, encripta e seta novamente na variavel;
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
		
		algorithm = Algorithm.HMAC256(secretKey.getBytes());
	}
	
	public TokenVO createAccessToken(String username, List<String> roles) {
		
		Date now = new Date(); //pegando a hora atual;
		Date validity = new Date(now.getTime() + validityInMilliseconds); //adiiciona mais uma hora apartir de quando o token for criado;
		var accessToken = getAccessToken(username, roles, now, validity);
		var refreshToken = getRefreshToken(username, roles, now);
		
		return new TokenVO(username, true, now, validity, accessToken, refreshToken);
	}

	//metodo que vai pegar o token criado
	private String getAccessToken(String username, List<String> roles, Date now, Date validity) {
		//seta no token a url de onde ele foi gerado;
		String issuerUrl = ServletUriComponentsBuilder
				.fromCurrentContextPath().build().toUriString();
		
		return JWT.create()
				.withClaim("roles", roles)
				.withIssuedAt(now)
				.withExpiresAt(validity)
				.withSubject(username)
				.withIssuer(issuerUrl)
				.sign(algorithm)
				.strip();
	}
	
	//metodo para reiniciar o token;
	private String getRefreshToken(String username, List<String> roles, Date now) {
		
		Date validityRefreshtoken = new Date(now.getTime() + validityInMilliseconds * 3);
		
		return JWT.create()
				.withClaim("roles", roles)
				.withIssuedAt(now)
				.withExpiresAt(validityRefreshtoken)
				.withSubject(username)
				.sign(algorithm)
				.strip();
	}
	
	public TokenVO refreshToken(String refreshToken) {
		if(refreshToken.contains("Bearer ")) {
			refreshToken = refreshToken.substring("Bearer ".length());
		}
		
		JWTVerifier verifier = JWT.require(algorithm).build();
		
		DecodedJWT decodedJWT = verifier.verify(refreshToken);
		
		String username = decodedJWT.getSubject(); //obtendo o sujeito no token;
		
		List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
		
		return createAccessToken(username, roles);
	}
	
	//meotodo para carregar os dados to token;
	public Authentication getAuthentication(String token) {
		DecodedJWT decodedJWT = decodedToken(token);
		
		UserDetails userDetails = this.userDetailsService
				.loadUserByUsername(decodedJWT.getSubject());
		
		return new UsernamePasswordAuthenticationToken(
				userDetails,
				"",
				userDetails.getAuthorities());
	}

	//esse metodo vai retirar as informações que estao dentro do token e coloca-las na forma de objetos;
	private DecodedJWT decodedToken(String token) {
		Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
		
		JWTVerifier verifier = JWT.require(algorithm).build();
		
		DecodedJWT decodedJWT = verifier.verify(token);
		
		return decodedJWT;
	}
	
	//metodo para retirar o token do cabeçalho da requisição;
	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring("Bearer ".length());
		}
		
		return null;
	}
	
	//valida se um token é valido;
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
