# Shitledge Server

Backend for **知屎 Shitledge** built with Spring Boot + Gradle + Java 17.

## Run locally

```bash
./gradlew bootRun --no-daemon
```

Default URL: `http://localhost:8081`

## Test

```bash
./gradlew test --no-daemon
```

## Profiles

- Default (`application.properties`): local H2 in-memory database
- `prod` (`application-prod.properties`): env-driven DB/port settings

Run with prod profile:

```bash
SPRING_PROFILES_ACTIVE=prod ./gradlew bootRun --no-daemon
```

## API

- `GET /api/health` -> `ok`
- `GET /api/articles?query=&page=0&size=6&sort=latest|oldest|title`
- `GET /api/articles/{id}`
- `POST /api/articles`

### Submit body

```json
{
  "title": "...",
  "author": "...",
  "email": "...",
  "summary": "...",
  "body": "..."
}
```
