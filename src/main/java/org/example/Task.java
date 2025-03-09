package org.example;

import lombok.*;

import java.time.*;

@Getter @Setter
@EqualsAndHashCode
@ToString
public class Task {

    private String name;
    private String description;
    private TaskStatus status;
    private final int id;
    private static int idCounter = 0;
    private LocalDate deadline;

    public Task (String name, String description, LocalDate deadline) {
        this.name = name;
        this.description = description;
        this.status = TaskStatus.TODO;
        this.deadline = deadline;
        this.id = generateID();
    }

    private int generateID() {
        return ++idCounter;
    }
}