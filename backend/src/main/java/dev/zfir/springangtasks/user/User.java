package dev.zfir.springangtasks.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="users", uniqueConstraints=@UniqueConstraint(columnNames="username"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String username;

    @Column(nullable=false)
    private String passwordHash;
}
