package dev.destiny.quiz_backend.quiz;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.destiny.quiz_backend.ai.DeepSeekService;
import dev.destiny.quiz_backend.ai.MistralAIService;
import dev.destiny.quiz_backend.dto.QuizDto;
import dev.destiny.quiz_backend.model.Prompter;
import dev.destiny.quiz_backend.model.Quiz;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class QuizService {
    private final DeepSeekService deepSeekService;
    private final MistralAIService mistralAIService;
    private final ObjectMapper mapper = new ObjectMapper();

    public QuizService(DeepSeekService deepSeekService, MistralAIService mistralAIService) {
        this.deepSeekService = deepSeekService;
        this.mistralAIService = mistralAIService;
    }

    public Mono<Quiz> generateQuiz(QuizDto quiz) {
        String prompt = Prompter.quizPrompt(quiz.type(), quiz.level(), quiz.numberOfQuestions());
        return mistralAIService.generateQuiz(prompt)
                .flatMap(this::validateAndParseQuiz);
    }

    private Mono<Quiz> validateAndParseQuiz(String jsonResponse) {
        try {
            // First parse the outer response structure
            JsonNode rootNode = mapper.readTree(jsonResponse);

            // Extract the content from the nested structure
            String quizContent = rootNode.path("choices")
                    .path(0)
                    .path("message")
                    .path("content")
                    .asText();

            // Then parse the actual quiz content
            Quiz quiz = mapper.readValue(quizContent, Quiz.class);
            return Mono.just(quiz);
        } catch (JsonProcessingException e) {
            System.out.println("Failed to parse JSON: " + jsonResponse);
            return Mono.error(new RuntimeException("Invalid JSON from AI", e));
        }
    }
}