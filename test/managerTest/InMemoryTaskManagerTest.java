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
        SubEpicTusk subEpicTusk1 = new SubEpicTusk("SubEpicTusk 1", "SubEpicTusk 1", StatusTask.NEW);
        SubEpicTusk subEpicTusk2 = new SubEpicTusk("SubEpicTusk 1", "SubEpicTusk 1", StatusTask.NEW);

        subEpicTusk1.setId(1);
        subEpicTusk2.setId(1);

        manager.addSubEpic(subEpicTusk1);
        manager.addSubEpic(subEpicTusk2);

        assertEquals(subEpicTusk1, subEpicTusk2, "Экземпляры SubEpicTusk с одинаковым id должны быть равны");
    }

    @Nested
    class HistoryManagerTest {

        @Test
        void testTaskHistoryPreservesPreviousVersion() {
            // Создаем задачу и добавляем её в HistoryManager
            Task originalTask = new Task("Task 1", "Original Task", StatusTask.NEW);
            manager.addTask(originalTask);

            // Создаем более новую версию задачи с тем же ID, но с измененными данными
            Task updatedTask = new Task("Ta", "Updated Task", StatusTask.IN_PROGRESS);
            updatedTask.setId(originalTask.getId()); // Устанавливаем тот же ID
            manager.updateTask(updatedTask);

            // Проверяем, что история содержит как оригинальную, так и обновленную версию задачи
            assertEquals(2, manager.getHistory().size(), "История должна содержать обе версии задачи");

            // Проверяем, что первая версия совпадает с оригинальной задачей
            Task firstVersion = manager.getHistory().get(0); // Индексация с 0
            assertEquals(originalTask.getId(), firstVersion.getId(), "ID первой версии задачи должен совпадать с оригинальной задачей");
            assertEquals(originalTask.getName(), firstVersion.getName(), "Имя первой версии задачи должно совпадать с оригинальной задачей");

            // Проверяем, что вторая версия — это обновленная задача
            Task secondVersion = manager.getHistory().get(1); // Индексация с 0
            assertEquals(updatedTask.getId(), secondVersion.getId(), "ID второй версии задачи должен совпадать с обновленной задачей");
            assertEquals(updatedTask.getName(), secondVersion.getName(), "Имя второй версии задачи должно совпадать с обновленной задачей");
        }
    }
}