package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class TaskHandlerTest {

    private TaskHandler taskHandler;
    private List<Task> taskList;

    @BeforeEach
    void setUp() {
        taskHandler = new TaskHandler();

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
        Task firstTask = taskList.get(0);
        Task secondTask = taskList.get(2);
        Task thirdTask = taskList.get(1);

        String firstTaskParameterToEdit = "name";
        String secondTaskParameterToEdit = "Status";
        String thirdTaskParameterToEdit = "deadline";

        String firstTaskNewValueOfName = "new Name";
        String secondTaskNewValueOfStatus = "IN_PROGRESS";
        String thirdTaskNewValueOfDeadline = "2025-01-01";

        taskHandler.editTaskHandler(taskList, firstTask.getId(),
                firstTaskParameterToEdit, firstTaskNewValueOfName);
        taskHandler.editTaskHandler(taskList, secondTask.getId(),
                secondTaskParameterToEdit, secondTaskNewValueOfStatus);
        taskHandler.editTaskHandler(taskList, thirdTask.getId(),
                thirdTaskParameterToEdit, thirdTaskNewValueOfDeadline);

        Assertions.assertEquals(firstTaskNewValueOfName, taskList.get(0).getName());
        Assertions.assertEquals(secondTaskNewValueOfStatus, taskList.get(2).getStatus().name());
        Assertions.assertEquals(LocalDate.parse(thirdTaskNewValueOfDeadline), taskList.get(1).getDeadline());
    }

    @Test
    void editTaskHandler_TestOfIncorrectlyEdit() {
        Task firstTask = taskList.get(0);
        Task secondTask = taskList.get(2);
        Task thirdTask = taskList.get(1);
        Task fourthTask = taskList.get(3);

        String firstTaskParameterToEdit = " ";
        String secondTaskParameterToEdit = "STAT";
        String thirdTaskParameterToEdit = "deadline";
        String fourthTaskParameterToEdit = "Name";

        String firstTaskDefaultValueOfName = taskList.get(0).getName();
        TaskStatus secondTaskDefaultValueOfStatus = taskList.get(2).getStatus();
        LocalDate thirdTaskDefaultValueOfDeadline = taskList.get(1).getDeadline();
        String fourthTaskDefaultValueOfName = taskList.get(3).getName();

        String firstTaskNewValueOfName = "new Action to do";
        String secondTaskNewValueOfStatus = "wtf?";
        String thirdTaskNewValueOfDeadline = "202512-01123-1213";
        String fourthTaskNewValueOfName = "new value of name";


        taskHandler.editTaskHandler(taskList, firstTask.getId(),
                firstTaskParameterToEdit, firstTaskNewValueOfName);
        taskHandler.editTaskHandler(taskList, secondTask.getId(),
                secondTaskParameterToEdit, secondTaskNewValueOfStatus);
        taskHandler.editTaskHandler(taskList, thirdTask.getId(),
                thirdTaskParameterToEdit, thirdTaskNewValueOfDeadline);
        taskHandler.editTaskHandler(taskList, fourthTask.getId() * 10,
                fourthTaskParameterToEdit, fourthTaskNewValueOfName);

        Assertions.assertEquals(firstTaskDefaultValueOfName, taskList.get(0).getName());
        Assertions.assertEquals(secondTaskDefaultValueOfStatus, taskList.get(2).getStatus());
        Assertions.assertEquals(thirdTaskDefaultValueOfDeadline, taskList.get(1).getDeadline());
        Assertions.assertEquals(fourthTaskDefaultValueOfName, taskList.get(3).getName());
    }

    @Test
    void removeTaskHandler_TestOfCorrectlyRemove() {
        int idTaskToDelete = taskList.get(1).getId();
        taskHandler.removeTaskHandler(taskList, idTaskToDelete);

        Assertions.assertTrue(taskList.stream().noneMatch(task -> task.getId() == idTaskToDelete));
    }

    @Test
    void removeTaskHandler_TestOfIncorrectlyRemove() {
        int idTaskToDelete = taskList.size() + 1; //т.к. ID инкрементальный такое значения существовать не может
        taskHandler.removeTaskHandler(taskList, idTaskToDelete);
        int testListLen = taskList.size();

        Assertions.assertEquals(testListLen, taskList.size()); //длина тест-массива должна остаться прежней
    }

    @Test
    void removeTaskHandler_TestOfRemoveFromEmptyList() {
        List<Task> emptyTaskList = new ArrayList<>();
        int firstListElement = 0;
        taskHandler.removeTaskHandler(emptyTaskList, firstListElement);

        Assertions.assertTrue(emptyTaskList.isEmpty());
    }

    @Test
    void filterTasksHandler_TestOfValidFilter_() {
        String statusForFilter = "TODO";

        //назначаем иные статусы некоторым задачам, чтобы проверить, отобразятся ли они
        taskList.get(0).setStatus(TaskStatus.IN_PROGRESS);
        taskList.get(2).setStatus(TaskStatus.DONE);

        List<Task> filteredTaskList = taskHandler.filterTasksHandler(taskList, statusForFilter);
        Assertions.assertEquals(2, filteredTaskList.size());
        Assertions.assertTrue(filteredTaskList.stream().
                allMatch(value -> value.getStatus() == TaskStatus.TODO));
    }

    @Test
    void filterTasksHandler_TestOfInvalidFilter() {
        // указываем некорректный фильтр
        String statusForFilter = "in progress";
        taskList.get(0).setStatus(TaskStatus.IN_PROGRESS);
        taskList.get(2).setStatus(TaskStatus.DONE);

        List<Task> filteredTaskList = taskHandler.filterTasksHandler(taskList, statusForFilter);
        Assertions.assertEquals(0, filteredTaskList.size()); //массив просто не заполнится
    }

    @Test
    void sortTasksHandler_TestOfSortWithIncorrectInput() {
        // указываем некорректное значение для сортировки
        String incorrectInputExample = "sort by description";
        List<Task> sortedTaskList = taskHandler.sortTasksHandler(taskList, incorrectInputExample);
        Assertions.assertTrue(sortedTaskList.isEmpty());
    }

    @Test
    void sortTasksHandler_TestOfSortByStatus() {
        // присваиваем иные статусы некоторым задачам для проверки корректности сортировки
        taskList.get(0).setStatus(TaskStatus.DONE);
        taskList.get(3).setStatus(TaskStatus.IN_PROGRESS);
        String sortParameter = "Status";

        List<Task> sortedTaskList = taskHandler.sortTasksHandler(taskList, sortParameter);
        Assertions.assertEquals(TaskStatus.TODO, sortedTaskList.get(0).getStatus());
        Assertions.assertEquals(TaskStatus.TODO, sortedTaskList.get(1).getStatus());
        Assertions.assertEquals(TaskStatus.IN_PROGRESS, sortedTaskList.get(2).getStatus());
        Assertions.assertEquals(TaskStatus.DONE, sortedTaskList.get(3).getStatus());
    }

    @Test
    void sortTasksHandler_TestOfSortByDeadline() {
        String sortParameter = "Deadline";
        List<Task> sortedTaskList = taskHandler.sortTasksHandler(taskList, sortParameter);
        Assertions.assertEquals(LocalDate.of(2025, 3, 10), sortedTaskList.get(0).getDeadline());
        Assertions.assertEquals(LocalDate.of(2025, 4, 1), sortedTaskList.get(1).getDeadline());
        Assertions.assertEquals(LocalDate.of(2025, 6, 12), sortedTaskList.get(2).getDeadline());
        Assertions.assertEquals(LocalDate.of(2025, 10, 29), sortedTaskList.get(3).getDeadline());
    }
}