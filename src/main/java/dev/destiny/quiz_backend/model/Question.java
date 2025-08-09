package dev.destiny.quiz_backend.model;

import java.util.List;

public record Question(
        String question,
        String answer,
        List<String> options
        ){}