package dev.destiny.quiz_backend.dto;

import dev.destiny.quiz_backend.model.Difficulty;
import dev.destiny.quiz_backend.model.Type;

import java.util.List;

public record QuizDto(
        Type type,
        int numberOfQuestions,
        Difficulty level
) {
}
