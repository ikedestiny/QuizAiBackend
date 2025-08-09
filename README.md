# ⚽ Football Quiz Generator API

![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2-green.svg)
![DeepSeek AI](https://img.shields.io/badge/Powered_by-DeepSeek_AI-blue.svg)
![License](https://img.shields.io/badge/License-MIT-orange.svg)

A modern Spring Boot API that generates football quizzes using AI (DeepSeek). Perfect for developers building quiz apps or learning AI integration.

## 🌟 Features

- **AI-Powered Quiz Generation** - Uses DeepSeek API to create dynamic quizzes
- **RESTful Endpoints** - Clean API design with JSON responses
- **Validation Layer** - Ensures consistent quiz structure
- **Async Non-Blocking** - Built with WebClient for high performance
- **Easy Configuration** - Environment variable support for API keys

## 🚀 Quick Start

### Prerequisites
- Java 17+
- DeepSeek API key (free)
- Maven/Gradle

### Installation
```bash
git clone https://github.com/yourusername/football-quiz-api.git
cd football-quiz-api
```

### Configuration
Create `.env` file:
```ini
DEEPSEEK_API_KEY=your_api_key_here
```

### Running the App
```bash
./mvnw spring-boot:run
```

## 📚 API Documentation

### Get a Football Quiz
```
GET /api/quizzes/football
```

**Response:**
```json
{
  "type": "FOOTBALL",
  "questions": [
    {
      "question": "Which country won the 2018 World Cup?",
      "answer": "France",
      "options": ["France", "Croatia", "Belgium", "England"]
    }
    // ...9 more questions
  ],
  "level": "MEDIUM"
}
```

## 🛠️ Development

### Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── yourpackage/
│   │           ├── controller/    # API endpoints
│   │           ├── service/       # Business logic
│   │           ├── model/         # Data structures
│   │           └── config/        # Configuration
│   └── resources/
│       ├── application.yml        # App config
│       └── .env                   # Secrets
└── test/                          # Unit tests
```

### Testing
```bash
./mvnw test
```

## 🤖 AI Integration Details

The system uses a carefully designed prompt to ensure consistent JSON output:

```text
Generate a football quiz with:
- 10 questions
- 4 options each
- Medium difficulty
- Strict JSON format matching {Quiz} model
```

## 🌍 Deployment

### Docker Build
```bash
docker build -t football-quiz-api .
docker run -p 8080:8080 -e DEEPSEEK_API_KEY football-quiz-api
```

### Cloud Deployment
[![Deploy to Heroku](https://www.herokucdn.com/deploy/button.svg)](https://heroku.com/deploy)

## 📜 License

MIT License - see [LICENSE](LICENSE) for details.

---

Made with ❤️ by [Ike Destiny] | [![Twitter Follow](https://img.shields.io/twitter/follow/yourhandle?style=social)](https://twitter.com/yourhandle)

**Contribute**: PRs welcome! Please open an issue first to discuss changes.