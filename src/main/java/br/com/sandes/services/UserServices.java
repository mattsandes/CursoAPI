package br.com.sandes.services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.sandes.repositories.UserRepository;

@Service
public class UserServices implements UserDetailsService{

	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	//injeção de dependencia por field
    @Autowired
    UserRepository userRepository;

    //injeção de dependencia por construtor;
    public UserServices(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Finding one user by name " + username + "!");
		
		var user = userRepository.findByUsername(username);
		
		if (user != null) {
			return user;
			
		} else {
			throw new UsernameNotFoundException("" + username + " not found!");
			
		}
	}
}
