package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TaskHandler {

    public void editTaskHandler(List<Task> taskList, int taskID, String parameterToEdit, String newValue) {

        Task taskToEdit = taskList.stream().filter(value ->
                value.getId() == taskID).findAny().orElse(null);

        if (taskToEdit == null) {
            System.out.println("task not found");
            return;
        }

        try {
            switch (TaskParameter.valueOf(parameterToEdit.toUpperCase())) {

                case NAME -> taskToEdit.setName(newValue);
                case DESCRIPTION -> {
                    taskToEdit.setDescription(newValue);
                }
                case STATUS -> {
                    try {
                        taskToEdit.setStatus(TaskStatus.valueOf(newValue));
                    } catch (IllegalArgumentException exception) {
                        System.out.println("incorrect value of status");
                    }
                }
                case DEADLINE -> {
                    try {
                        taskToEdit.setDeadline(LocalDate.parse(newValue));
                    } catch (DateTimeParseException exception) {
                        System.out.println("incorrect input format");
                    }
                }
                default -> System.out.println("task not found");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println("Invalid parameter");
        }
    }

    public void removeTaskHandler(List<Task> taskList, int idToDelete) {
        Task taskToDelete = taskList.stream().filter(value ->
                value.getId() == idToDelete).findFirst().orElse(null);

        if (taskToDelete != null) {
            System.out.println(taskList);
            taskList.remove(taskToDelete);
            System.out.println(taskList);
            System.out.println("task was deleted");
        } else {
            System.out.println("can't find task");
        }
    }

    public List<Task> filterTasksHandler(List<Task> taskList, String status) {
        return taskList.stream().filter(value -> value.getStatus().name().equalsIgnoreCase(status)).toList();
    }

    public List<Task> sortTasksHandler(List<Task> taskList, String sortBy) {
        try {
            return switch (TaskParameter.valueOf(sortBy.toUpperCase())) {
                case STATUS -> taskList.stream()
                        .sorted(Comparator.comparing(value -> value.getStatus().getPriority())).toList();
                case DEADLINE -> taskList.stream().sorted(Comparator.comparing(Task::getDeadline)).toList();
                default -> new ArrayList<>();
            };
        } catch (IllegalArgumentException exception) {
            System.out.println("Invalid sort parameter");
            return new ArrayList<>();
        }
    }
}
