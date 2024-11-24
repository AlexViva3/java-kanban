import datapacks.EpicTusk;
import datapacks.StatusTask;
import datapacks.SubEpicTusk;
import datapacks.Task;
import management.ManagerTasks;

public class Main {
    public static void main(String[] args) {
        ManagerTasks manager = new ManagerTasks();

        Task task1 = new Task("Завтрак", "Приготовить завтрак", StatusTask.NEW);
        Task task2 = new Task("Обед", "Приготовить обед", StatusTask.NEW);
        EpicTusk epic1 = new EpicTusk("Написать код проекта", "Написать 2 проекта", StatusTask.NEW);
        SubEpicTusk subEpic1 = new SubEpicTusk("Написать проект спринта 4", "Написать проект спринта ", StatusTask.NEW);
        SubEpicTusk subEpic2 = new SubEpicTusk("Написать проект спринта 5", "Написать проект спринта ", StatusTask.NEW);
        EpicTusk epic2 = new EpicTusk("Заказать вещи", "Заказать вещи", StatusTask.NEW);
        SubEpicTusk subEpic3 = new SubEpicTusk("Заказать шорты", "Заказать шорты", StatusTask.NEW);
        


    }
}