package com.example.demo.Repositories;
import com.example.demo.Entities.Scale;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScaleRepository extends JpaRepository<Scale, Long> {
    Scale findByTitle(String title);

    List<Scale> findByAdminIdAndIsDeleted(int adminId, boolean isDeleted);

}
