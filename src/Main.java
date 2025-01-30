import datapacks.EpicTusk;
import datapacks.StatusTask;
import datapacks.SubEpicTusk;
import datapacks.Task;
import history.HistoryManager;
import management.InMemoryTaskManager;
import management.Managers;
import management.TaskManager;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();

        Task task1 = new Task("Завтрак", "Приготовить завтрак", StatusTask.NEW);
        Task task2 = new Task("Обед", "Приготовить обед", StatusTask.NEW);
        EpicTusk epic1 = new EpicTusk("Написать код проекта", "Написать 2 проекта", StatusTask.NEW);
        SubEpicTusk subEpic1 = new SubEpicTusk("Написать проект спринта 4", "Написать проект спринта ", StatusTask.NEW);
        SubEpicTusk subEpic2 = new SubEpicTusk("Написать проект спринта 5", "Написать проект спринта ", StatusTask.NEW);
        EpicTusk epic2 = new EpicTusk("Заказать вещи", "Заказать вещи", StatusTask.NEW);
        SubEpicTusk subEpic3 = new SubEpicTusk("Заказать шорты", "Заказать шорты", StatusTask.NEW);
        manager.addTask(task1);
        manager.addTask(task2);
        manager.addEpic(epic1);
        manager.addEpic(epic2);
        manager.addSubEpic(subEpic1);
        manager.addSubEpic(subEpic2);
        manager.addSubEpic(subEpic3);
        manager.removeTask(1);

    }
}