package com.worktracker.repository;

import com.worktracker.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    boolean existsById(Integer id);

    boolean existsByName(String name);

    void deleteById(Integer id);
}
