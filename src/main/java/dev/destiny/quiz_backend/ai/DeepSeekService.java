package dev.destiny.quiz_backend.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class DeepSeekService {
    private final WebClient webClient;

    public  DeepSeekService (
            WebClient.Builder webClientBuilder,
            @Value("${deepseek.api.key}") String apiKey,
            @Value("{deepseek.api.url}") String apiUrl
    ){
        this.webClient = webClientBuilder
                .baseUrl(apiUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public Mono<String> generateQuiz(String prompt){
        // Define the request body (adjust based on DeepSeek's API spec)
        String requestBody = """
            {
                "model": "deepseek-chat",
                "messages": [
                    {
                        "role": "user",
                        "content": "%s"
                    }
                ],
                "temperature": 0.7
            }
            """.formatted(prompt);

        return webClient.post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class); // Parse as String first for validation
    }
}
