package com.worktracker.repository;

import com.worktracker.model.Token;
import com.worktracker.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {

    boolean existsByToken(String token);

    @Query(value = "select u.user_type as userType "
            + "       from users u "
            + "       join token t on t.user_id = u.id "
            + "     where t.token = ?1", nativeQuery = true)
    UserType getUserTypeByToken(String token);
}
