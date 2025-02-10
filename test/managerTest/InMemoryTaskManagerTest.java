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

    @Nested
    class HistoryManagerTest {

        @Test
        void testTaskHistoryPreservesPreviousVersion() {
            Task originalTask = new Task("Task 1", "Оригинальная задача", StatusTask.NEW);
            manager.addTask(originalTask);


            assertEquals(1, manager.getHistory().size(), "История должна содержать оригинальную задачу");


            Task updatedTask = new Task("Обновить задачу", "Обновить название", StatusTask.IN_PROGRESS);
            updatedTask.setId(originalTask.getId());
            manager.updateTask(updatedTask);


            assertEquals(2, manager.getHistory().size(), "История должна содержать обе версии задачи");


            Task firstVersion = manager.getHistory().get(0);
            assertEquals(originalTask.getId(), firstVersion.getId(), "ID первой версии задачи должен совпадать с оригинальной задачей");
            assertEquals(originalTask.getName(), firstVersion.getName(), "Имя первой версии задачи должно совпадать с оригинальной задачей");


            Task secondVersion = manager.getHistory().get(1);
            assertEquals(updatedTask.getId(), secondVersion.getId(), "ID второй версии задачи должен совпадать с обновленной задачей");
            assertEquals(updatedTask.getName(), secondVersion.getName(), "Имя второй версии задачи должно совпадать с обновленной задачей");
        }
    }
}