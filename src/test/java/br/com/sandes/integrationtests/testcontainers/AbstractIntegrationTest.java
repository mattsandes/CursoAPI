package br.com.sandes.integrationtests.testcontainers;

import java.util.Map;
import java.util.stream.Stream;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {

	public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext>{
		
		static PostgreSQLContainer<?> postgresql = new PostgreSQLContainer<>("postgres:16.2");
		
		private static void startContainers() {
			Startables.deepStart(Stream.of(postgresql)).join();
		}

		private static Map<String, String> createConnectionConfigutration() {
			return Map.of(
					"spring.datasource.url", postgresql.getJdbcUrl(),
					"spring.datasource.username", postgresql.getUsername(),
					"spring.datasource.password", postgresql.getPassword());
		}
		
		@SuppressWarnings({"unchecked", "rawtypes"})
		public void initialize(ConfigurableApplicationContext applicationContext) {
			startContainers();
			
			ConfigurableEnvironment environment = applicationContext.getEnvironment();
			
			MapPropertySource testcontainer = new MapPropertySource(
					"testcontainers",
					(Map) createConnectionConfigutration());
			
			environment.getPropertySources().addFirst(testcontainer);
		}

	}

}
