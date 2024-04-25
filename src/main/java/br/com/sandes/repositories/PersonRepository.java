package br.com.sandes.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.sandes.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{
	
	@Modifying
	@Query("UPDATE Person p SET p.enabled = false WHERE p.id = :id") //Query que vai pegar os usuarios pelo nome deles no banco de dados;
	void disablePerson(@Param(value = "id") Long id);

	//Esse usar essa query pode trazer problemas de performance se o banco de dados tiver registros extensivos;
	//caso essa situação aconteça, deve ser decidido com o mantenedor do banco de dados para ver qual a melhor solução;
	@Query("SELECT p FROM Person p WHERE LOWER(p.firstName) LIKE LOWER(CONCAT('%', :firstName, '%'))")
	Page<Person> findPeronsByName(@Param("firstName") String firstName, Pageable pageable);
}
