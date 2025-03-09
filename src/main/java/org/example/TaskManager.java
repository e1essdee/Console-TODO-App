package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    private final TaskHandler taskHandler = new TaskHandler();
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

        taskHandler.editTaskHandler(taskList, taskID, parameterToEdit, newValue);
    }

    public void removeTask(List<Task> taskList) {
        System.out.println("enter the name of the task to delete: \n" + taskList);
        int idToDelete = scanner.nextInt();
        taskHandler.removeTaskHandler(taskList, idToDelete);
    }

    public void filterTasks(List<Task> taskList) {
        System.out.println("Tasks with what status you want to display");
        String status = scanner.nextLine();
        List<Task> filteredTasksList = taskHandler.filterTasksHandler(taskList, status);

        if (filteredTasksList.isEmpty()) {
            System.out.println("there are no tasks with this status");
        } else {
            System.out.println(filteredTasksList);
        }
    }

    public void sortTasks(List<Task> taskList) {
        System.out.println("Choose how you want to sort tasks: by deadline or status");
        String sortBy = scanner.nextLine();
        List<Task> sortTasksList = taskHandler.sortTasksHandler(taskList, sortBy);

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
}