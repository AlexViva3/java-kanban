package management;

import datapacks.EpicTusk;
import datapacks.SubEpicTusk;
import datapacks.Task;

import java.util.ArrayList;
import java.util.List;


public interface TaskManager {
    void addTask(Task task);

    ArrayList<Task> getAllTasks();

    void removeTask(Integer id);

    void removeAllTasks();

    void updateTask(Task task);

    Task takeTaskForId(int id);

    void addEpic(EpicTusk epic);

    void epicCheckStatus(EpicTusk epic);

    void updateEpic(EpicTusk epic);

    void removeEpic(int id);

    void removeAllEpic();

    EpicTusk getEpic(int id);

    ArrayList<EpicTusk> getAllEpic();

    void addSubEpic(SubEpicTusk subEpic);

    ArrayList<SubEpicTusk> getSubEpicTasks();

    void updateSubEpic(SubEpicTusk subEpic);

    void removeSubtaskById(int id, ArrayList<EpicTusk> epics);

    void removeAllSubEpic();

    ArrayList<SubEpicTusk> getSubEpicsByEpicId(int id);

    List<Task> getHistory();

    SubEpicTusk getSubEpic(int id);
}
