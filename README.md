# FootballScope

FootballScope is a Spring Boot application that fetches football match data from the Football Data API, stores it in PostgreSQL, and presents it through a simple web dashboard and REST API.

The project is designed for tracking match information, league context, and team data with a clean, lightweight backend. It currently focuses on Champions League fixtures, but the structure is ready to extend to other competitions.

## Features

- Fetches match data from the Football Data API
- Stores teams, leagues, and match records in PostgreSQL
- Exposes data via a REST API
- Displays matches in a Thymeleaf-based UI
- Supports filtering by league and match status
- Keeps the data model organized with JPA entities and repositories

## Tech Stack

- Java 17
- Spring Boot 4.1.0
- Spring Data JPA
- PostgreSQL
- Thymeleaf
- Gradle

## Project Overview

The app follows a simple layered architecture:

- `controllers` expose HTTP routes for the API and web pages
- `services` handle communication with the external API and core business logic
- `repositories` manage persistence with JPA
- `models` define the database entities
- `dto` packages model API responses and transfer objects
- `templates` provide the browser-based match views

## Prerequisites

Before running the project, make sure you have:

- Java 17 or newer
- Gradle
- PostgreSQL running locally
- A valid Football Data API token from `https://www.football-data.org/`

## Configuration

Update the application settings in `src/main/resources/application.properties`:

```properties
football.api.url=https://api.football-data.org/v4
football.api.token=YOUR_FOOTBALL_DATA_API_TOKEN

spring.application.name=FootballScope
spring.datasource.url=jdbc:postgresql://localhost:5432/footballscope
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Notes:

- Create a PostgreSQL database named `footballscope` (or update the URL to match your database name)
- Keep API tokens out of version control in a real production setup
- You can replace the hardcoded property-based setup with environment variables if needed

## Running the Project

From the project root:

```bash
./gradlew bootRun
```

The application will start on:

```text
http://localhost:8080
```

## Web UI

Open the browser and visit:

- `http://localhost:8080/matches` for the match list
- `http://localhost:8080/matches/{id}` for a specific match details page

The page automatically syncs data before showing the current list of matches.

## API Endpoints

### Sync data

```http
GET /api/sync
```

This triggers the service to fetch the latest matches from the Football Data API and save them to the database.

### Match endpoints

```http
GET /api/matches
GET /api/matches/{id}
GET /api/matches/league/{leagueId}
GET /api/matches/status/{status}
GET /api/matches/league/{leagueId}/status/{status}
```

Example:

```bash
curl http://localhost:8080/api/matches
curl http://localhost:8080/api/matches/1
curl http://localhost:8080/api/matches/status/FINISHED
```

## Project Structure

```text
FootballScope/
├── src/
│   ├── main/
│   │   ├── java/com/example/footballscope/
│   │   │   ├── controllers/
│   │   │   ├── dto/
│   │   │   ├── mappers/
│   │   │   ├── models/
│   │   │   ├── repositories/
│   │   │   ├── services/
│   │   │   ├── web/
│   │   │   └── FootballScopeApplication.java
│   │   ├── resources/
│   │   │   ├── static/
│   │   │   ├── templates/
│   │   │   └── application.properties
│   │   └── test/
│   └── build.gradle
├── gradlew
├── gradlew.bat
├── settings.gradle
├── HELP.md
└── README.md
```

## Testing

Run the test suite with:

```bash
./gradlew test
```

## Future Improvements

- Add support for more competitions and seasons
- Improve dashboard styling and filtering
- Add pagination and search
- Add authentication and user settings
- Add automated integration tests for the API and sync flow

## License

This project is currently provided as an educational/demo project without a formal license. If you plan to publish it publicly, add an appropriate open-source license such as MIT or Apache 2.0.

## Contributing

Contributions are welcome. If you want to improve the project:

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Open a pull request with a clear summary

## Support

For questions or suggestions, open an issue in the repository or contact the project maintainer.
