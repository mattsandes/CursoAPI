package br.com.sandes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.sandes.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	@Query("SELECT u FROM User u WHERE u.userName = :userName") //Query que vai pegar os usuarios pelo nome deles no banco de dados;
	User findByUsername(@Param(value = "userName") String userName);
}
