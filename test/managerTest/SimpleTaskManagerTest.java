package management;

import datapacks.EpicTusk;
import datapacks.SubEpicTusk;
import datapacks.StatusTask;
import datapacks.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleTaskManagerTest {
    TaskManager manager = Managers.getDefault();

    @Test
    void testAddAndGetTask() {
        Task task = new Task("Купить молоко", "В магазине у дома", StatusTask.NEW);
        manager.addTask(task);

        Task savedTask = manager.takeTaskForId(task.getId());
        assertEquals(task.getName(), savedTask.getName(), "Названия задач должны совпадать");
        assertEquals(task.getDescription(), savedTask.getDescription(), "Описания задач должны совпадать");
    }

    @Test
    void testAddTwoTasksHaveDifferentIds() {
        Task task1 = new Task("Task 1", "Description", StatusTask.NEW);
        Task task2 = new Task("Task 2", "Description", StatusTask.NEW);

        manager.addTask(task1);
        manager.addTask(task2);

        assertNotEquals(task1.getId(), task2.getId(), "ID задач должны отличаться");
    }

    @Test
    void testEpicWithSubtask() {
        EpicTusk epic = new EpicTusk("Ремонт", "Сделать ремонт в квартире", StatusTask.NEW);
        manager.addEpic(epic);

        SubEpicTusk subtask = new SubEpicTusk("Купить краску", "Белая матовая", StatusTask.NEW, epic.getId());
        manager.addSubEpic(subtask);

        List<SubEpicTusk> subtasks = manager.getSubEpicsByEpicId(epic.getId());
        assertEquals(1, subtasks.size(), "У эпика должна быть одна подзадача");
        assertEquals("Купить краску", subtasks.get(0).getName(), "Название подзадачи должно совпадать");
    }

    @Test
    void testTaskStatusChange() {
        Task task = new Task("Task", "Description", StatusTask.NEW);
        manager.addTask(task);

        task.setStatus(StatusTask.DONE);
        manager.updateTask(task);

        assertEquals(StatusTask.DONE, manager.takeTaskForId(task.getId()).getStatus(),
                "Статус задачи должен измениться на DONE");
    }

    @Test
    void testDeleteTask() {
        Task task = new Task("Task to delete", "Description", StatusTask.NEW);
        manager.addTask(task);

        manager.removeTask(task.getId());

        assertNull(manager.takeTaskForId(task.getId()), "Задача должна быть удалена");
    }
}
