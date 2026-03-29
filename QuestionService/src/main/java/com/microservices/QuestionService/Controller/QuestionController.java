package com.microservices.QuestionService.Controller;

import com.microservices.QuestionService.Model.Question;
import com.microservices.QuestionService.Model.QuestionWrapper;
import com.microservices.QuestionService.Model.Response;
import com.microservices.QuestionService.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return  questionService.addQuestion(question);
    }
      @GetMapping("generate")
    public ResponseEntity<List<Integer>>getQuestionForQuiz(@RequestParam String categoryName, @RequestParam Integer numQuestion){
        return questionService.getQuestionForQuiz(categoryName,numQuestion);
      }
@PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>>getQuestionFromId(@RequestBody List<Integer>questionId ){
return questionService.getQuestionFromId(questionId);
}
@PostMapping("getScore")
    public ResponseEntity<Integer>getScore(@RequestBody List<Response>responses){
        return questionService. getScore(responses);
}

}