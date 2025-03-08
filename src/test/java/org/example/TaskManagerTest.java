package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class TaskManagerTest {

    private TaskManager taskManager;
    private List<Task> taskList;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();

        Task task1 = new Task("Action", "some description",
                LocalDate.of(2025, 6, 12));
        Task task2 = new Task("Walk", "just go outside of my house",
                LocalDate.of(2025, 10, 29));
        Task task3 = new Task("Dota", "lets play dota",
                LocalDate.of(2025, 3, 10));
        Task task4 = new Task("what?", "what?",
                LocalDate.of(2025, 4, 1));

        taskList = new ArrayList<>();
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);
        taskList.add(task4);
    }

    @Test
    void editTaskHandler_TestOfCorrectlyEdit() {
        taskManager.editTaskHandler(taskList, taskList.get(0).getId(),
                "name", "new Action to do");
        taskManager.editTaskHandler(taskList, taskList.get(2).getId(),
                "status", "IN_PROGRESS");
        taskManager.editTaskHandler(taskList, taskList.get(1).getId(),
                "deadline", "2025-01-01");

        Assertions.assertEquals("new Action to do", taskList.get(0).getName());
        Assertions.assertEquals("IN_PROGRESS", taskList.get(2).getStatus().name());
        Assertions.assertEquals(LocalDate.parse("2025-01-01"), taskList.get(1).getDeadline());
    }

    @Test
    void editTaskHandler_TestOfIncorrectlyEdit() {
        taskManager.editTaskHandler(taskList, taskList.get(0).getId(),
                " ", "new Action to do");
        taskManager.editTaskHandler(taskList, taskList.get(2).getId(),
                "Status", "wtf?");
        taskManager.editTaskHandler(taskList, taskList.get(2).getId(),
                "deadline", "2912-122-134");

        Assertions.assertEquals("Action", taskList.get(0).getName());
        Assertions.assertEquals("TODO", taskList.get(2).getStatus().name());
        Assertions.assertEquals(LocalDate.parse("2025-03-10"), taskList.get(2).getDeadline());
    }

    @Test
    void removeTaskHandler_TestOfCorrectlyRemove() {
        int idTaskToDelete = taskList.get(1).getId();
        taskManager.removeTaskHandler(taskList, idTaskToDelete);

        Assertions.assertFalse(taskList.stream().anyMatch(task -> task.getId() == idTaskToDelete));
    }

    @Test
    void removeTaskHandler_TestOfIncorrectlyRemove() {
        int idTaskToDelete = 11273; //в методе генерации ID значения находятся в диапазоне 0-1000
        taskManager.removeTaskHandler(taskList, idTaskToDelete);

        Assertions.assertEquals(4, taskList.size()); //длина тест-массива == 4 и должна остаться прежней
    }

    @Test
    void removeTaskHandler_TestOfRemoveFromEmptyList() {
        List<Task> emptyTaskList = new ArrayList<>();
        taskManager.removeTaskHandler(emptyTaskList, 1);

        Assertions.assertTrue(emptyTaskList.isEmpty());
    }

    @Test
    void filterTasksHandler_TestOfValidFilter_() {
        String statusForFilter = "TODO";
        taskList.get(0).setStatus(Task.TaskStatus.IN_PROGRESS);
        taskList.get(2).setStatus(Task.TaskStatus.DONE);

        List<Task> filteredTaskList = taskManager.filterTasksHandler(taskList, statusForFilter);
        Assertions.assertEquals(2, filteredTaskList.size());
        Assertions.assertTrue(filteredTaskList.stream().
                allMatch(value -> value.getStatus() == Task.TaskStatus.TODO));
    }

    @Test
    void filterTasksHandler_TestOfInvalidFilter() {
        String statusForFilter = "in progress";
        taskList.get(0).setStatus(Task.TaskStatus.IN_PROGRESS);
        taskList.get(2).setStatus(Task.TaskStatus.DONE);

        List<Task> filteredTaskList = taskManager.filterTasksHandler(taskList, statusForFilter);
        Assertions.assertEquals(0, filteredTaskList.size()); //массив просто не заполнится
    }

    @Test
    void sortTasksHandler_TestOfSortWithIncorrectInput() {
        String incorrectInputExample = "sort by description";
        List<Task> sortedTaskList = taskManager.sortTasksHandler(taskList, incorrectInputExample);
        Assertions.assertNull(sortedTaskList);
    }

    @Test
    void sortTasksHandler_TestOfSortByStatus() {
        taskList.get(0).setStatus(Task.TaskStatus.DONE);
        taskList.get(3).setStatus(Task.TaskStatus.IN_PROGRESS);

        List<Task> sortedTaskList = taskManager.sortTasksHandler(taskList, "status");
        Assertions.assertTrue(sortedTaskList.get(0).getStatus() == Task.TaskStatus.TODO &&
                sortedTaskList.get(1).getStatus() == Task.TaskStatus.TODO &&
                sortedTaskList.get(2).getStatus() == Task.TaskStatus.IN_PROGRESS &&
                sortedTaskList.get(3).getStatus() == Task.TaskStatus.DONE);
    }

    @Test
    void sortTasksHandler_TestOfSortByDeadline() {
        List<Task> sortedTaskList = taskManager.sortTasksHandler(taskList, "deadline");
        Assertions.assertTrue(sortedTaskList.get(0).getDeadline().
                equals(LocalDate.of(2025, 3, 10)) &&
                sortedTaskList.get(1).getDeadline().equals(LocalDate.of(2025, 4, 1)) &&
                sortedTaskList.get(2).getDeadline().equals(LocalDate.of(2025, 6, 12)) &&
                sortedTaskList.get(3).getDeadline().equals(LocalDate.of(2025, 10, 29)));

    }
}