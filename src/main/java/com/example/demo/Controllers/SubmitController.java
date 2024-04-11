package com.example.demo.Controllers;
import com.example.demo.Entities.Scale;
import com.example.demo.Entities.Question;
import com.example.demo.Entities.Questionnaire;
import com.example.demo.Entities.Option;
import com.example.demo.Repositories.ScaleRepository;
import com.example.demo.Repositories.QuestionRepository;
import com.example.demo.Repositories.OptionRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SubmitController {

    @Autowired
    private ScaleRepository scaleRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionRepository optionRepository;

    @PostMapping("/submit")
    public void submitQuestionnaire(@RequestBody Questionnaire questionnaire) {
        // Save the scale
        Scale scale = new Scale();
        scale.setTitle(questionnaire.getScale().getTitle().toString());
        scaleRepository.save(scale);

        // Save the questions
        List<Question> questions = questionnaire.getQuestions();
        for (Question question : questions) {
            question.setScale(scale.getTitle());


            List<Option> options = question.getOptions();


            question.setOptions(null);
            questionRepository.save(question);
            

            // Save the options
            
            if (options != null) {
                for (Option option : options) {
                    option.setQuestion(question);
                    optionRepository.save(option);
                }
            }

            question.setOptions(options);
            question = questionRepository.save(question);
            
        }
    }
}