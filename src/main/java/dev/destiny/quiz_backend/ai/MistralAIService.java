package dev.destiny.quiz_backend.ai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MistralAIService {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public MistralAIService(
            WebClient.Builder webClientBuilder,
            @Value("${mistral.ai.api.key}") String apiKey,
            @Value("${mistral.ai.api.url}") String apiUrl,
            ObjectMapper objectMapper
    ) {
        this.objectMapper = objectMapper;
        this.webClient = webClientBuilder
                .baseUrl(apiUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Mono<String> generateQuiz(String prompt) {
        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("model", "mistral-tiny"); // or "mistral-small", "mistral-medium" depending on your needs

        ObjectNode message = objectMapper.createObjectNode();
        message.put("role", "user");
        message.put("content", prompt);

        requestBody.putArray("messages").add(message);
        requestBody.put("temperature", 0.7);
        requestBody.put("max_tokens", 2048);
        requestBody.put("stream", false); // Mistral API typically expects this

        return webClient.post()
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new RuntimeException(
                                "Mistral AI API error: " + response.statusCode() + " - " + errorBody
                        ))))
                .bodyToMono(String.class);
    }

    // Additional method for streaming if needed
    public Mono<String> generateQuizStream(String prompt) {
        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("model", "mistral-tiny");

        ObjectNode message = objectMapper.createObjectNode();
        message.put("role", "user");
        message.put("content", prompt);

        requestBody.putArray("messages").add(message);
        requestBody.put("temperature", 0.7);
        requestBody.put("max_tokens", 2048);
        requestBody.put("stream", true);

        return webClient.post()
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new RuntimeException(
                                "Mistral AI API error: " + response.statusCode() + " - " + errorBody
                        ))))
                        .bodyToMono(String.class);
    }
}