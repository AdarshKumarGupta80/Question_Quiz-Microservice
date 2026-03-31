package com.microservices.QuestionService.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Question title cannot be empty")
    private String questionTitle;

    @NotBlank(message = "Option 1 cannot be empty")
    private String option1;

    @NotBlank(message = "Option 2 cannot be empty")
    private String option2;

    @NotBlank(message = "Option 3 cannot be empty")
    private String option3;

    @NotBlank(message = "Option 4 cannot be empty")
    private String option4;

    @NotBlank(message = "Right answer cannot be empty")
    private String rightAnswer;

    @NotBlank(message = "Difficulty level cannot be empty")
    private String difficultylevel;

    @NotBlank(message = "Category cannot be empty")
    private String category;
}