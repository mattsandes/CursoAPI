package br.com.sandes.configs;

public class TestConfigs {

	//definindo a contante para a porta que o restAssured usara quando os testes de integração for iniciado;
	public static final int SERVER_PORT = 8888;
	
	//adicionando a constante de origem;
	public static final String HEADER_PARAM_ORIGIN = "Origin";
	
	//contante para autenticação;
	public static final String HEADER_PARAM_AUTHORIZATION = "Authorization";
	
	//constante para tipo de conteudo;
	public static final String CONTENT_TYPE_JSON = "application/json";
	public static final String CONTENT_TYPE_XML = "application/xml";
	public static final String CONTENT_TYPE_YAML = "application/x-yaml";
	
	//variaeis de origem
	public static final String ORIGIN_ERUDIO = "https://erudio.com.br";
	public static final String ORIGIN_SEMERU = "https://semeru.com.br";
}
