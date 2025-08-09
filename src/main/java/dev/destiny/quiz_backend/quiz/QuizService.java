package dev.destiny.quiz_backend.quiz;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.destiny.quiz_backend.ai.DeepSeekService;
import dev.destiny.quiz_backend.dto.QuizDto;
import dev.destiny.quiz_backend.model.Prompter;
import dev.destiny.quiz_backend.model.Quiz;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class QuizService {
    private final DeepSeekService deepSeekService;


    public QuizService(DeepSeekService deepSeekService) {
        this.deepSeekService = deepSeekService;
    }

    public Mono<Quiz> generateQuiz(QuizDto quiz){
        String prompt = Prompter.quizPrompt(quiz.type(),quiz.level(),quiz.numberOfQuestions());
        return deepSeekService.generateQuiz(prompt)
                .flatMap(this::validateAndParseQuiz);
    }

    private Mono<Quiz> validateAndParseQuiz(String jsonResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Quiz quiz = mapper.readValue(jsonResponse, Quiz.class);
            return Mono.just(quiz);
        } catch (JsonProcessingException e) {
            return Mono.error(new RuntimeException("Invalid JSON from AI"));
        }
    }
}
