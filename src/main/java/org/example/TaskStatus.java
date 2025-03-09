package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
enum TaskStatus {
    TODO(1),
    IN_PROGRESS(2),
    DONE(3);

    private final int priority;
}