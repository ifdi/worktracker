package com.worktracker.repository;

import com.worktracker.model.TypeUser;
import com.worktracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByTypeUser(TypeUser typeUser);
}
