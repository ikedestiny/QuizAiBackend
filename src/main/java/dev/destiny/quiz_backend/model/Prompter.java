package dev.destiny.quiz_backend.model;

public class Prompter {

    public static String quizPrompt(Type type, Difficulty level, Integer numberOfQuestions) {
        return String.format("""
            IMMEDIATELY RETURN ONLY THIS JSON STRUCTURE - NO OTHER TEXT, NO NOTES, NO EXPLANATIONS:
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
            
            STRICT INSTRUCTIONS:
            1. Generate exactly %d questions in the EXACT format above
            2. NEVER add any text outside the JSON structure
            3. NEVER include markdown or code blocks
            4. Each question must have 4 distinct options
            5. The correct answer must be among the options
            6. All text must be properly escaped for JSON
            7. Difficulty must strictly match "%s"
            8. Only include factual %s content
            9. If you can't comply, return empty JSON {}
            10. This will be parsed by strict Java JSON parser - invalid JSON will break the system
            
            REMEMBER: ONLY THE RAW JSON OUTPUT BETWEEN THE CURLY BRACES, NOTHING ELSE
            """,
                type, level, numberOfQuestions, level, type);
    }
}