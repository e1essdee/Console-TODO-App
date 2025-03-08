package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppLauncher {

    public void launch() {

        List<Task> taskList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();
        String action = "";
        taskManager.showMenu();

        while (!action.equals("exit")) {
            action = scanner.nextLine();

            switch (action.toLowerCase()) {

                case "add":
                    taskManager.addNewTask(taskList);
                    break;

                case "list":
                    System.out.println(taskList);
                    break;

                case "edit":
                    taskManager.editTask(taskList);
                    break;

                case "delete":
                    taskManager.removeTask(taskList);
                    break;

                case "filter":
                    taskManager.filterTasks(taskList);
                    break;

                case "sort":
                    taskManager.sortTasks(taskList);
                    break;

                case "help":
                    taskManager.showMenu();
                    break;

                case "exit":
                    break;

                default:
                    System.out.println("Incorrect input, try another command");
                    break;
            }
        }
        scanner.close();
    }
}