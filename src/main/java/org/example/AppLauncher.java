package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class AppLauncher {

    public void launch() {

        List<Task> taskList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();
        Action action = null;
        taskManager.showMenu();

        while (action != Action.valueOf("exit".toUpperCase())) {
            try {
                action = Action.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException exception) {
                System.out.println("Incorrect input, try another command ");
            }

            switch (Objects.requireNonNull(action)) {
                case ADD -> taskManager.addNewTask(taskList);
                case LIST -> System.out.println(taskList);
                case EDIT -> taskManager.editTask(taskList);
                case DELETE -> taskManager.removeTask(taskList);
                case FILTER -> taskManager.filterTasks(taskList);
                case SORT -> taskManager.sortTasks(taskList);
                case HELP -> taskManager.showMenu();
                case EXIT -> System.out.println("Good bye!");
                default -> System.out.println("Incorrect input, try another command");
            }
        }
        scanner.close();
    }
}