package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    private final Scanner scanner = new Scanner(System.in);

    public void addNewTask(List<Task> taskList) {

        System.out.println("enter the name of task: ");
        String taskName = scanner.nextLine();

        System.out.println("enter a task description: ");
        String taskDescription = scanner.nextLine();

        System.out.println("enter the deadline for the task: ");
        try {
            LocalDate taskDeadline = LocalDate.parse(scanner.nextLine());
            taskList.add(new Task(taskName, taskDescription, taskDeadline));
        } catch (DateTimeParseException exception) {
            System.out.println("Incorrect date. Try again.");
        }
    }

    public void editTask(List<Task> taskList) {

        System.out.println("enter task id to edit: \n" + taskList );
        int taskID = scanner.nextInt();
        scanner.nextLine();
        System.out.println("""
                            what do you want to edit?
                            - name
                            - description
                            - status
                            - deadline""");
        String parameterToEdit = scanner.nextLine();
        System.out.println("enter new value: ");
        String newValue = scanner.nextLine();

        editTaskHandler(taskList, taskID, parameterToEdit, newValue);
    }

    public void removeTask(List<Task> taskList) {
        System.out.println("enter the name of the task to delete: \n" + taskList);
        int idToDelete = scanner.nextInt();
        removeTaskHandler(taskList, idToDelete);
    }

    public void filterTasks(List<Task> taskList) {
        System.out.println("Tasks with what status you want to display");
        String status = scanner.nextLine();
        List<Task> filteredTasksList = filterTasksHandler(taskList, status);

        if (filteredTasksList.isEmpty()) {
            System.out.println("there are no tasks with this status");
        } else {
            System.out.println(filteredTasksList);
        }
    }

    public void sortTasks(List<Task> taskList) {
        System.out.println("Choose how you want to sort tasks: by deadline or status");
        String sortBy = scanner.nextLine();
        List<Task> sortTasksList = sortTasksHandler(taskList, sortBy);

        if (sortTasksList.isEmpty()) {
            System.out.println("incorrect sort type");
        } else {
            System.out.println(sortTasksList);
        }
    }

    public void showMenu() {
        System.out.println("""
                    choose an action:\s
                    - add – add task.
                    - list – show list of tasks.
                    - edit – edit task.
                    - delete – remove task.
                    - filter – filter tasks by status.
                    - sort – sort tasks
                    - help - show commands
                    - exit – logout.""");
    }

    public void editTaskHandler(List<Task> taskList, int taskID, String parameterToEdit, String newValue) {

        Task taskToEdit = taskList.stream().filter(value ->
                value.getId() == taskID).findAny().orElse(null);

        switch (parameterToEdit.toLowerCase()) {

            case "name":
                assert taskToEdit != null;
                taskToEdit.setName(newValue);
                break;
            case "description":
                assert taskToEdit != null;
                taskToEdit.setDescription(newValue);
                break;
            case "status":
                assert taskToEdit != null;
                try {
                    taskToEdit.setStatus(Task.TaskStatus.valueOf(newValue));
                } catch (IllegalArgumentException exception) {
                    System.out.println("incorrect value of status");
                }

                break;
            case "deadline":
                try {
                    assert taskToEdit != null;
                    taskToEdit.setDeadline(LocalDate.parse(newValue));
                } catch (DateTimeParseException exception) {
                    System.out.println("incorrect input format");
                }
                break;
            default:
                System.out.println("task not found");
        }
    }

    public void removeTaskHandler(List<Task> taskList, int idToDelete) {
        Task taskToDelete = taskList.stream().filter(value ->
                value.getId() == idToDelete).findAny().orElse(null);

        if (taskList.contains(taskToDelete)) {
            taskList.remove(taskToDelete);
            System.out.println("task was deleted");
        } else {
            System.out.println("can't find task");
        }
    }

    public List<Task> filterTasksHandler(List<Task> taskList, String status) {
        return taskList.stream().filter(value -> value.getStatus().name().equalsIgnoreCase(status)).toList();
    }

    public List<Task> sortTasksHandler(List<Task> taskList, String sortBy) {
        return switch (sortBy) {
            case "status" -> taskList.stream()
                    .sorted(Comparator.comparing(value -> value.getStatus().getPriority())).toList();
            case "deadline" -> taskList.stream().sorted(Comparator.comparing(Task::getDeadline)).toList();
            default -> null;
        };
    }
}