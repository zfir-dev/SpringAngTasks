package dev.zfir.springangtasks.task;

import dev.zfir.springangtasks.user.User;
import dev.zfir.springangtasks.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class TaskController {

    private final TaskRepository tasks;
    private final UserRepository users;

    public TaskController(TaskRepository tasks, UserRepository users) {
        this.tasks = tasks;
        this.users = users;
    }

    private User currentUser(Authentication auth) {
        return (User) auth.getPrincipal();
    }

    @PostMapping
    public TaskResponse create(@Valid @RequestBody TaskCreateRequest req, Authentication auth) {
        User user = currentUser(auth);
        Task task = tasks.save(Task.builder()
                .title(req.title())
                .description(req.description())
                .status(TaskStatus.PENDING)
                .user(user)
                .build());
        
        return TaskResponse.from(task);
    }

    @GetMapping
    public List<TaskResponse> myTasks(Authentication auth) {
        return tasks.findByUser(currentUser(auth))
                .stream()
                .map(TaskResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public TaskResponse getOne(@PathVariable Long id, Authentication auth) {
        Task task = tasks.findByIdAndUser(id, currentUser(auth))
                .orElseThrow(() -> new RuntimeException("Task not found"));
        
        return TaskResponse.from(task);
    }

    @PutMapping("/{id}")
    public TaskResponse update(@PathVariable Long id,
                               @Valid @RequestBody TaskUpdateRequest req,
                               Authentication auth) {
        Task task = tasks.findByIdAndUser(id, currentUser(auth))
                .orElseThrow(() -> new RuntimeException("Task not found"));
        
        task.setTitle(req.title());
        task.setDescription(req.description());
        task.setStatus(req.status());
        tasks.save(task);
        
        return TaskResponse.from(task);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, Authentication auth) {
        Task task = tasks.findByIdAndUser(id, currentUser(auth))
                .orElseThrow(() -> new RuntimeException("Task not found"));
        
        tasks.delete(task);
    }
}
