package com.example.demo.Repositories;
import com.example.demo.Entities.Answer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByAnswerRecordId(Long answerRecordId);
}