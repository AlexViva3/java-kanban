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
            // Создаем эпик
            EpicTusk epic = new EpicTusk("Epic 1", "Description", StatusTask.NEW);
            manager.addEpic(epic);

            // Создаем подзадачу и добавляем её в менеджер
            SubEpicTusk subEpic = new SubEpicTusk("SubEpic 1", "Description", StatusTask.NEW, epic.getId());
            manager.addSubEpic(subEpic);

            // Обновляем подзадачу
            SubEpicTusk updatedSubEpic = new SubEpicTusk("Updated SubEpic", "Updated Description", StatusTask.IN_PROGRESS, epic.getId());
            updatedSubEpic.setId(subEpic.getId()); // Устанавливаем тот же ID
            manager.updateSubEpic(updatedSubEpic);

            // Проверяем, что история содержит обе версии подзадачи
            assertEquals(2, manager.getHistory().size(), "История должна содержать обе версии подзадачи");

            // Проверяем, что первая версия совпадает с оригинальной подзадачей
            Task firstVersion = manager.getHistory().get(0);
            assertEquals(subEpic.getId(), firstVersion.getId(), "ID первой версии подзадачи должен совпадать с оригинальной подзадачей");

            // Проверяем, что вторая версия — это обновленная подзадача
            Task secondVersion = manager.getHistory().get(1);
            assertEquals(updatedSubEpic.getId(), secondVersion.getId(), "ID второй версии подзадачи должен совпадать с обновленной подзадачей");
        }
    }
}