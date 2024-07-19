package com.example.demo.Controllers;
import com.example.demo.Entities.Scale;
import com.example.demo.Entities.Question;
import com.example.demo.Entities.Questionnaire;
import com.example.demo.Entities.Option;
import com.example.demo.Repositories.ScaleRepository;
import com.example.demo.Repositories.QuestionRepository;
import com.example.demo.Repositories.OptionRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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
    public String submitQuestionnaire(@RequestBody Questionnaire questionnaire, Model model) {
        // Save the scale
        Scale sourceScale = questionnaire.getScale();
        Scale scale = new Scale();
        scale.setTitle(sourceScale.getTitle().toString());
        scale.setTotalPage(sourceScale.getTotalPage());
        scale.setDeleted(false);
        scale.setCreatedDate(java.time.LocalDateTime.now());
        scale.setAdminId(sourceScale.getAdminId());
        scale.setPreviousId(sourceScale.getPreviousId());
        if (!sourceScale.getCGroupBgColor().isEmpty()) {
            scale.setCGroupBgColor(sourceScale.getCGroupBgColor());
        }
        if (!sourceScale.getCGroupMusic().isEmpty()) {
            scale.setCGroupMusic(sourceScale.getCGroupMusic());
        }
        if (!sourceScale.getEGroupBgColor().isEmpty()) {
            scale.setEGroupBgColor(sourceScale.getEGroupBgColor());
        }
        if (!sourceScale.getEGroupMusic().isEmpty()) {
            scale.setEGroupMusic(sourceScale.getEGroupMusic());
        }
        scaleRepository.save(scale);

        // Save the questions
        List<Question> questions = questionnaire.getQuestions();
        for (Question question : questions) {
            question.setScale(scale);

            // Extract the options from the question
            List<Option> options = question.getOptions();

            // Remove the options to avoid a constraint violation
            question.setOptions(null);
            questionRepository.save(question);

            // Save the options
            if (options != null) {
                for (Option option : options) {
                    option.setQuestion(question);
                    optionRepository.save(option);
                }
            }

            // Save the question with the options
            question.setOptions(options);
            question = questionRepository.save(question);
            
        }

        // Find the previous scale and set it as deleted
        if (scale.getPreviousId() != null) {
            Optional<Scale> previousScaleOptional = scaleRepository.findById(scale.getPreviousId());
            if (previousScaleOptional.isPresent()) {
                Scale previousScale = previousScaleOptional.get();
                previousScale.setDeleted(true);
                previousScale.setExpiryDate(java.time.LocalDateTime.now()); // Convert LocalDate to LocalDateTime
                scaleRepository.save(previousScale);
            }
        }

        String response = String.format("Dashboard?AdminId=%s", scale.getAdminId());

        return response;
    }
}