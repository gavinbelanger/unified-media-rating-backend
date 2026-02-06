# Unified Media Rating Backend (MyUMRP)

A Spring Boot backend application for unified media rating and tracking.

## Setup

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- PostgreSQL database

### Configuration

1. Copy the example properties file:
   ```bash
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   ```

2. Edit `src/main/resources/application.properties` and add your credentials:
   - Database password
   - TMDB API key (from https://www.themoviedb.org/settings/api)
   - RAWG API key (from https://rawg.io/apidocs)

**⚠️ Important:** Never commit `application.properties` to version control. It's already in `.gitignore` to prevent accidental commits.

### Building and Running

```bash
# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

## API Endpoints

- `/api/search` - Search for media
- `/api/media` - Media management
- `/api/ratings` - Rating management
- `/api/watchlist` - Watchlist management
- `/api/users` - User management
- `/api/import` - Media import functionality
- `/api/test` - API testing endpoints
