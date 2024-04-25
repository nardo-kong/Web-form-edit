package com.example.demo.Repositories;
import com.example.demo.Entities.AnswerRecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface AnswerRecordRepository extends JpaRepository<AnswerRecord, Long> {
    // @Query("SELECT COUNT(a) FROM AnswerRecord a WHERE a.scal.id = :scaleId AND a.completed = :completed")
    int countByScaleIdAndCompleted(Long scaleId, boolean completed);
}