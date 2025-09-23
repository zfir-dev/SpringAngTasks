package dev.zfir.springangtasks.task;

public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status
) {
    public static TaskResponse from(Task t) {
        return new TaskResponse(
                t.getId(),
                t.getTitle(),
                t.getDescription(),
                t.getStatus()
        );
    }
}
