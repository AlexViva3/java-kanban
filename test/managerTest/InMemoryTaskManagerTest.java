package management;

import datapacks.EpicTusk;
import datapacks.SubEpicTusk;
import datapacks.StatusTask;
import datapacks.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleTaskManagerTest {
    // Создаём менеджер для тестов
    TaskManager manager = Managers.getDefault();

    @Test
    void testAddAndGetTask() {
        // 1. Создаём задачу
        Task task = new Task("Купить молоко", "В магазине у дома", StatusTask.NEW);

        // 2. Добавляем в менеджер
        manager.addTask(task);

        // 3. Проверяем, что задача добавилась
        Task savedTask = manager.getTaskById(task.getId());

        // 4. Проверяем, что это та же самая задача
        assertEquals(task, savedTask, "Задачи должны быть одинаковыми");
    }

    @Test
    void testAddTwoTasksHaveDifferentIds() {
        Task task1 = new Task("Task 1", "Description", StatusTask.NEW);
        Task task2 = new Task("Task 2", "Description", StatusTask.NEW);

        manager.addTask(task1);
        manager.addTask(task2);

        // Проверяем, что ID разные
        assertNotEquals(task1.getId(), task2.getId(), "ID задач должны отличаться");
    }

    @Test
    void testEpicWithSubtask() {
        // 1. Создаём эпик
        EpicTusk epic = new EpicTusk("Ремонт", "Сделать ремонт в квартире", StatusTask.NEW);
        manager.addEpic(epic);

        // 2. Создаём подзадачу для этого эпика
        SubEpicTusk subtask = new SubEpicTusk("Купить краску", "Белая матовая", StatusTask.NEW, epic.getId());
        manager.addSubEpic(subtask);

        // 3. Проверяем, что подзадача привязана к эпику
        assertEquals(epic.getId(), subtask.getEpicID(), "Подзадача должна быть привязана к эпику");

        // 4. Проверяем, что эпик знает о своей подзадаче
        assertEquals(1, manager.getSubEpicsByEpicId(epic.getId()).size(),
                "У эпика должна быть одна подзадача");
    }

    @Test
    void testTaskStatusChange() {
        Task task = new Task("Task", "Description", StatusTask.NEW);
        manager.addTask(task);

        // Меняем статус
        task.setStatus(StatusTask.DONE);
        manager.updateTask(task);

        // Проверяем, что статус изменился
        assertEquals(StatusTask.DONE, manager.getTaskById(task.getId()).getStatus(),
                "Статус задачи должен измениться на DONE");
    }

    @Test
    void testDeleteTask() {
        Task task = new Task("Task to delete", "Description", StatusTask.NEW);
        manager.addTask(task);

        // Удаляем задачу
        manager.deleteTask(task.getId());

        // Проверяем, что задача удалилась
        assertNull(manager.getTaskById(task.getId()), "Задача должна быть удалена");
    }
}