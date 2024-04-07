package com.example.demo.Repositories;
import com.example.demo.Entities.Administrator;

import org.springframework.data.jpa.repository.JpaRepository;




public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {
    Administrator findByName(String name);
}