package com.example.demo.Services;
import com.example.demo.Entities.Question;
import com.example.demo.Repositories.QuestionRepository;
import com.example.demo.Repositories.ScaleRepository;
import com.example.demo.Entities.Scale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Iterator;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private ScaleRepository scaleRepository;

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }
    
    public List<Question> getQuestionsByScaleTitleRemoveIllustration(Long id) {
        // 1. 根据scale-id查询scale
        Scale scale = scaleRepository.findById(id).get();
    
        // 2. 从scale对象中获取title
        String scaleTitle = scale.getTitle();
    
        // 3. 根据scale的title查询question
        List<Question> questions = questionRepository.findByScale(scaleTitle);
    
        // 4. 使用迭代器移除所有type为"illustration"的问题
        Iterator<Question> iterator = questions.iterator();
        while (iterator.hasNext()) {
            Question question = iterator.next();
            if ("illustration".equals(question.getType())) {
                iterator.remove();
            }
        }
    
        // 5. 返回修改后的列表
        return questions;
    }
}
