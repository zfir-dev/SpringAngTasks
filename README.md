# SpringAngTasks

A simple Full Stack Task Manager built with:
- **Spring Boot** (Java 17, JWT, H2, Spring Security) → Backend
- **Angular** → Frontend  
- **Docker Compose** → Run both apps together

## Getting Started

### lone the Repository
```bash
git clone https://github.com/<your-username>/SpringAngTasks.git
cd SpringAngTasks
```

### Run with Docker Compose
```bash
docker-compose up --build
```

- **Backend** → http://localhost:8081
- **Frontend** → http://localhost:4200

The backend exposes the H2 console at: http://localhost:8081/h2 with
- JDBC URL: jdbc:h2:mem:taskdb
- Username: sa
- Password: (empty)

## Frontend Usage

1. Open http://localhost:4200 in your browser.
2. Register a new user or log in with existing credentials.
3. Once logged in, you'll see the task dashboard where you can:
   - Create new tasks
   - View your tasks
   - Update task status (Pending → Completed)
   - Delete tasks

## Testing the Backend with cURL

Once the backend is running, you can test it using the following commands.

### Register a User
```bash
curl -X POST http://localhost:8081/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"zafir","password":"pass123"}'
```

### Login (get a token)
```bash
TOKEN=$(curl -s -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"zafir","password":"pass123"}' | jq -r .token)

echo $TOKEN
```

### Create a Task
```bash
curl -X POST http://localhost:8081/api/tasks \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"title":"Deploy the app","description":"Using Docker Compose"}'
```

### Get All Tasks
```bash
curl -X GET http://localhost:8081/api/tasks \
  -H "Authorization: Bearer $TOKEN"
```

### Get Task by ID
```bash
curl -X GET http://localhost:8081/api/tasks/1 \
  -H "Authorization: Bearer $TOKEN"
```

### Update Task
```bash
curl -X PUT http://localhost:8081/api/tasks/1 \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"title":"Deploy backend","description":"Spring Boot on Docker","status":"COMPLETED"}'
```

### Delete Task
```bash
curl -X DELETE http://localhost:8081/api/tasks/1 \
  -H "Authorization: Bearer $TOKEN"
```

## Tips

- **Export your token after login** so you don't have to copy-paste it:
```bash
TOKEN=$(curl -s -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"zafir","password":"pass123"}' | jq -r .token)
```

- **Use jq to pretty-print JSON responses**:
```bash
curl -s -X GET http://localhost:8081/api/tasks -H "Authorization: Bearer $TOKEN" | jq
```
