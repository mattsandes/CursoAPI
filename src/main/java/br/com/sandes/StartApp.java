package br.com.sandes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StartApp {

	public static void main(String[] args) {
		SpringApplication.run(StartApp.class, args);
		
//		Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = 
//				new Pbkdf2PasswordEncoder("",
//						8,
//						185000,
//						SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
//		
//        Map<String, PasswordEncoder> encoders = new HashMap<>();
//        
//        encoders.put("pbkdf2", pbkdf2PasswordEncoder);
//        
//        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
//        
//        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2PasswordEncoder);
//        
//        String result1 = passwordEncoder.encode("admin123");
//        String result2 = passwordEncoder.encode("admin234");
//        
//        System.out.println("My hash " + result1);
//        System.out.println("My hash " + result2);
	}

}
