package dev.destiny.quiz_backend.model;

import java.util.List;

public record Quiz(
        Type type,
        List<Question> questions,
        Difficulty level
) {
}
