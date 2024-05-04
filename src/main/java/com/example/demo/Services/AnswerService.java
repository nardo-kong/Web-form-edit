package com.example.demo.Services;
import com.example.demo.Entities.Answer;
import com.example.demo.Entities.Question;
import com.example.demo.Repositories.AnswerRepository;
import com.example.demo.Repositories.QuestionRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    public List<Answer> getAnswersByRecordId(Long recordId) {
        return answerRepository.findByAnswerRecordId(recordId);
    }

    public List<Question> getQuestionsByScaleId(Long scaleId) {
        // 根据scaleId查询Question
        // 这里假设QuestionRepository有一个findByScaleId方法
        return questionRepository.findById(scaleId);
    }
}