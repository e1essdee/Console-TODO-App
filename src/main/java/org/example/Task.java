package org.example;

import lombok.*;

import java.time.*;

@Getter @Setter
@EqualsAndHashCode
@ToString
public class Task {

    @Getter
    @AllArgsConstructor
     enum TaskStatus {
        TODO(1),
        IN_PROGRESS(2),
        DONE(3);

        private final int priority;
    }

    private String name;
    private String description;
    private TaskStatus status;
    private int id;
    private LocalDate deadline;

    public Task (String name, String description, LocalDate deadline) {
        this.name = name;
        this.description = description;
        this.status = TaskStatus.TODO;
        this.deadline = deadline;
        this.id = generateID();
    }

    private int generateID() {
        return (int) (Math.random() * 1000);
    }
}