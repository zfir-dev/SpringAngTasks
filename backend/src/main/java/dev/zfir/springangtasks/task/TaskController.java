package dev.zfir.springangtasks.task;

import dev.zfir.springangtasks.user.User;
import dev.zfir.springangtasks.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
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
        User u = currentUser(auth);
        Task t = tasks.save(Task.builder()
                .title(req.title())
                .description(req.description())
                .status(TaskStatus.PENDING)
                .user(u)
                .build());
        return TaskResponse.from(t);
    }

    @GetMapping
    public List<TaskResponse> myTasks(Authentication auth) {
        return tasks.findByUser(currentUser(auth)).stream().map(TaskResponse::from).toList();
    }

    @GetMapping("/{id}")
    public TaskResponse getOne(@PathVariable Long id, Authentication auth) {
        Task t = tasks.findByIdAndUser(id, currentUser(auth))
                .orElseThrow(() -> new RuntimeException("Not found"));
        return TaskResponse.from(t);
    }

    @PutMapping("/{id}")
    public TaskResponse update(@PathVariable Long id,
                               @Valid @RequestBody TaskUpdateRequest req,
                               Authentication auth) {
        Task t = tasks.findByIdAndUser(id, currentUser(auth))
                .orElseThrow(() -> new RuntimeException("Not found"));
        t.setTitle(req.title());
        t.setDescription(req.description());
        t.setStatus(req.status());
        tasks.save(t);
        return TaskResponse.from(t);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, Authentication auth) {
        Task t = tasks.findByIdAndUser(id, currentUser(auth))
                .orElseThrow(() -> new RuntimeException("Not found"));
        tasks.delete(t);
    }
}
