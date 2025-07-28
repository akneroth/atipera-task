# atipera-task

## Technologies Used

- Java 21
- Spring Boot 3.5.4
- org.json
- JUnit 5

### Prerequisites

- Java 21+
- Gradle

### Build, Run, Test

```powershell
./gradlew bootRun

./gradlew test
```

### API Usage

- **GET** `/getNonForkedRepos?user={username}`  
  Returns a JSON array of repositories or an error object.

#### Example Success Response

```json
[
  {
    "repoName": "example-repo",
    "login": "username",
    "branches": [
      {
        "name": "main",
        "lastCommitSha": "abc123"
      }
    ]
  }
]
```

#### Example Error Response

```json
{
  "status": 404,
  "message": "Not Found"
}
```