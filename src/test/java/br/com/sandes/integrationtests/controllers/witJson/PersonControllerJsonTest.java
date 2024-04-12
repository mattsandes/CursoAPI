package br.com.sandes.integrationtests.controllers.witJson;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.sandes.configs.TestConfigs;
import br.com.sandes.data.vo.v1.security.TokenVO;
import br.com.sandes.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.sandes.integrationtests.vo.AccountCredentialsVO;
import br.com.sandes.integrationtests.vo.PersonVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest{

	private static RequestSpecification specification;
	private static ObjectMapper mapper;
	
	private static PersonVO person;
	
	@BeforeAll
	static void setUp() {
		mapper = new ObjectMapper();
		
		//essa propriedade vai desabilitar falhas quando o
		//teste encontrar propriedades que nao estao pesentes no escopo do vo;
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		person = new PersonVO();
	}
	
	@Test
	@Order(0)
	public void authorization() throws JsonMappingException, JsonProcessingException {
		//instancia de account credentials que sera a request feita para o endpint;
		AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");
		
		//implementação do teste
		var accessToken = given()
				.basePath("/auth/signin") //path para fazer a requisição;
					.port(TestConfigs.SERVER_PORT) //porta que o teste ser executada;
					.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(user)
					.when()
				.post()
					.then()
						.statusCode(200)
							.extract()
							.body()
								.as(TokenVO.class)
							.getAccessToken();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		//implementação do teste
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
					.body(person)
					.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		//lendo o objeto criado;
		PersonVO createdPerson = mapper.readValue(content, PersonVO.class);
		person = createdPerson;
		
		assertTrue(createdPerson.getId() > 0);
		assertNotNull(createdPerson.getId());
		assertNotNull(createdPerson.getFirst_name());
		assertNotNull(createdPerson.getLast_name());
		assertNotNull(createdPerson.getGender());
		assertNotNull(createdPerson.getAddress());
		
		assertEquals("Richard", createdPerson.getFirst_name());
		assertEquals("Stallman", createdPerson.getLast_name());
		assertEquals("Male", createdPerson.getGender());
		assertEquals("New York City, New York, US", createdPerson.getAddress());
	}

	//criando um metodo que vai emular o person;
	private void mockPerson() {
		person.setFirst_name("Richard");
		person.setLast_name("Stallman");
		person.setAddress("New York City, New York, US");
		person.setGender("Male");
	}
}
