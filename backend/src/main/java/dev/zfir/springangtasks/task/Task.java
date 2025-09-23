package dev.zfir.springangtasks.task;

import dev.zfir.springangtasks.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="tasks")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Task {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String title;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    private User user;
}
