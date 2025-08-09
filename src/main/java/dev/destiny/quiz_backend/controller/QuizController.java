package dev.destiny.quiz_backend.controller;

import dev.destiny.quiz_backend.dto.QuizDto;
import dev.destiny.quiz_backend.model.Quiz;
import dev.destiny.quiz_backend.quiz.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/quiz")
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/new")
    public Mono<Quiz> getQuiz(@RequestBody QuizDto dto){
        return quizService.generateQuiz(dto);
    }
}
