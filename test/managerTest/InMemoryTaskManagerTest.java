package management;
import datapacks.EpicTusk;
import datapacks.SubEpicTusk;
import datapacks.StatusTask;
import datapacks.Task;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    TaskManager manager = Managers.getDefault();


    @Test
    void addTask() {
        Task task1 = new Task("Task 1", "Task 1", StatusTask.NEW);
        manager.addTask(task1);
        manager.addTask(task1);
        assertEquals(task1, task1, "Экземпляры Task с одинаковым id должны быть равны");
    }

    @Test
    void addEpic() {
        EpicTusk epicTusk = new EpicTusk("EpicTusk 1", "EpicTusk 1", StatusTask.NEW);
        manager.addEpic(epicTusk);
        manager.addEpic(epicTusk);
        assertEquals(epicTusk, epicTusk, "Экземпляры EpicTusk с одинаковым id должны быть равны");
    }

    @Test
    void addSubEpicsTusk() {
        EpicTusk epic = new EpicTusk("Epic 1", "Description", StatusTask.NEW);
        manager.addEpic(epic);
        int epicID = epic.getId();

        SubEpicTusk subEpicTusk1 = new SubEpicTusk("SubEpicTusk 1", "SubEpicTusk 1", StatusTask.NEW, epicID);
        SubEpicTusk subEpicTusk2 = new SubEpicTusk("SubEpicTusk 2", "SubEpicTusk 2", StatusTask.NEW, epicID);

        manager.addSubEpic(subEpicTusk1);
        manager.addSubEpic(subEpicTusk2);

        assertEquals(2, manager.getSubEpicTasks().size(), "Должно быть две подзадачи");

        assertEquals(epicID, subEpicTusk1.getEpicID(), "Подзадача 1 должна быть привязана к эпику с ID " + epicID);
        assertEquals(epicID, subEpicTusk2.getEpicID(), "Подзадача 2 должна быть привязана к эпику с ID " + epicID);
    }
}