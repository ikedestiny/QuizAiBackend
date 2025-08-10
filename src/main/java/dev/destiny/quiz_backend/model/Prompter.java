package dev.destiny.quiz_backend.model;

public class Prompter {

    public static String quizPrompt(Type type, Difficulty level, Integer numberOfQuestions) {
        return String.format("""
            Generate a football quiz in JSON format strictly following this structure:
            {
              "type": "%s",
              "questions": [
                {
                  "question": "[Question text]",
                  "answer": "[Correct answer]",
                  "options": ["[Option1]", "[Option2]", "[Option3]", "[Option4]"]
                }
              ],
              "level": "%s"
            }

            Rules:
            1. Generate exactly %d questions.
            2. Each question must have 4 options, one of which is the correct answer.
            3. The difficulty level must be "%s".
            4. Only include factual %s knowledge (no jokes or opinions).
            5. Ensure options are plausible distractors.
            6. Escape special JSON characters.
            7. Do not include any explanations or notes—only pure JSON.
            8. Make sure the response is correct valid json. No extra stuff or introductory text. I am parsing it using a java parser that needs json in the format i gave above
            """, type, level, numberOfQuestions, level, type);
    }
}