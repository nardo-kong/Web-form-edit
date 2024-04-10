package com.example.demo.Entities;

import java.util.List;

public class Questionnaire {
    private Scale scale;
    private List<Question> questions;
    private List<Option> options;

    // Getters and setters...
    public Scale getScale() {
        return scale;
    }
    public List<Question> getQuestions() {
        return questions;
    }

    public List<Option> getOptions() {
        return options;
    }
}
