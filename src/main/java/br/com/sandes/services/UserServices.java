package br.com.sandes.services;

import br.com.sandes.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserServices implements UserDetailsService {

    private Logger logger = Logger.getLogger(UserServices.class.getName());

    @Autowired
	UserRepository userRepository; //injecao de dependencia via propriedade;

	public UserServices(UserRepository userRepository) { //injeçao de dependencia via contrutor; Ler sobre depois;
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Finding one user by name " + username + "!");

		var user = userRepository.findByUserName(username);

		if(user != null){
			return user;
		}
		else {
			throw new UsernameNotFoundException("" + username + " not found!");

		}
	}
}
