package br.com.sandes.repositories;

import br.com.sandes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM Users WHERE u.userName =:userName")
    User findByUserName(@Param("userName") String userName);
}
