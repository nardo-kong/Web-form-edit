package com.example.demo.Repositories;
import com.example.demo.Entities.AnswerRecord;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface AnswerRecordRepository extends JpaRepository<AnswerRecord, Long> {
    // @Query("SELECT COUNT(a) FROM AnswerRecord a WHERE a.scal.id = :scaleId AND a.completed = :completed")
    int countByScaleIdAndCompleted(Long scaleId, boolean completed);

    List<AnswerRecord> findByScaleId(Long scaleId);
    
    List<AnswerRecord> findByScaleId(Long scaleId, Sort sort);

    Optional<AnswerRecord> findById(Long recordId);


}