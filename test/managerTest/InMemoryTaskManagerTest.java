package management;

import datapacks.EpicTusk;
import datapacks.SubEpicTusk;
import datapacks.StatusTask;
import datapacks.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    TaskManager manager = Managers.getDefault();

    @Test
    void addTask() {
        Task task1 = new Task("Task 1", "Task 1", StatusTask.NEW);
        manager.addTask(task1);
        Task savedTask = manager.takeTaskForId(task1.getId());
        assertEquals(task1.getName(), savedTask.getName(), "Названия задач должны совпадать");
    }

    @Test
    void addEpic() {
        EpicTusk epic = new EpicTusk("Epic 1", "Description", StatusTask.NEW);
        manager.addEpic(epic);

        EpicTusk savedEpic = manager.getEpicById(epic.getId());
        assertNotNull(savedEpic, "Эпик должен быть найден");
        assertEquals(epic.getName(), savedEpic.getName(), "Названия эпиков должны совпадать");
    }

    @Test
    void addSubEpic() {
        EpicTusk epic = new EpicTusk("Epic 1", "Description", StatusTask.NEW);
        manager.addEpic(epic);

        SubEpicTusk subTask = new SubEpicTusk("Sub 1", "Sub 1", StatusTask.NEW, epic.getId());
        manager.addSubEpic(subTask);

        assertEquals(epic.getId(), subTask.getEpicId(), "ID эпика должен совпадать");
    }

    @Test
    void deleteTask() {
        Task task = new Task("Task to delete", "Desc", StatusTask.NEW);
        manager.addTask(task);
        manager.removeTask(task.getId());
        assertNull(manager.takeTaskForId(task.getId()), "Задача должна быть удалена");
    }
}