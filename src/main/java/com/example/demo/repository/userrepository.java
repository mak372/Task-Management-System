package com.example.demo.repository;
import com.example.demo.entity.*;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userrepository extends JpaRepository<task,Integer> {
	Optional<task> findByUsername(String username);

}
