import datapacks.EpicTusk;
import datapacks.StatusTask;
import datapacks.Task;
import management.Managers;
import management.TaskManager;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        Task task1 = new Task("Завтрак", "Приготовить завтрак", StatusTask.NEW);
        Task task2 = new Task("Обед", "Приготовить обед", StatusTask.NEW);
        EpicTusk epic1 = new EpicTusk("Написать код проекта", "Написать 2 проекта", StatusTask.NEW);

        manager.addTask(task1);
        manager.addTask(task2);
        manager.addEpic(epic1);

        manager.takeTaskForId(task1.getId());
        manager.takeTaskForId(task2.getId());
        manager.getEpic(epic1.getId());

        System.out.println("История задач: " + manager.getHistory());
    }
}