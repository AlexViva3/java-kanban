package management;
import datapacks.EpicTusk;
import datapacks.SubEpicTusk;
import datapacks.Task;
import datapacks.StatusTask;
import history.HistoryManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, EpicTusk> epics = new HashMap<>();
    private final HashMap<Integer, SubEpicTusk> subEpics = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    private int nextId = 0;

    @Override
    public void addTask(Task task) {
        task.setId(nextId++);
        tasks.put(task.getId(), task);

    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void removeTask(Integer id) {
        Task task = tasks.remove(id);
        historyManager.remove(task.getId());

    }

    @Override
    public void removeAllTasks() {
        for (Task task : tasks.values()) {
            historyManager.remove(task.getId());
        }
        tasks.clear();
    }

    @Override
    public void updateTask(Task task) {

        tasks.put(task.getId(), task);

    }

    @Override
    public Task takeTaskForId(int id) {
        if (tasks.containsKey(id)) {
            historyManager.addTaskHistory(tasks.get(id));
        } else {
            System.out.println("Задача с ID " + id + " не существует. Добавление в историю невозможно.");
        }
        return tasks.get(id);
    }

    @Override
    public void addEpic(EpicTusk epic) {
        epic.setId(nextId++);
        epics.put(epic.getId(), epic);

    }

    @Override
    public void epicCheckStatus(EpicTusk epic) {
        int newStat = 0;
        for (SubEpicTusk subtask : getSubEpicTasks()) {
            if (subtask.getStatus() == StatusTask.IN_PROGRESS) {
                epic.setStatus(StatusTask.IN_PROGRESS);
                return;
            }
            if (subtask.getStatus() == StatusTask.NEW) {
                newStat++;
            }
        }
        if (newStat == getSubEpicTasks().size()) {
            epic.setStatus(StatusTask.NEW);
        } else {
            epic.setStatus(StatusTask.DONE);
        }

    }

    @Override
    public void updateEpic(EpicTusk epic) {
        epics.put(epic.getId(), epic);

        epicCheckStatus(epic);

    }

    @Override
    public void removeEpic(int id) {
        EpicTusk epic = epics.remove(id);
        for (Integer subEpicId : epic.getEpicIds()) {
            SubEpicTusk subEpic = subEpics.remove(subEpicId);
            historyManager.remove(subEpic.getId());
        }
    }

    @Override
    public void removeAllEpic() {
        for (EpicTusk epic : epics.values()) {
            for (Integer subEpicId : epic.getEpicIds()) {
                SubEpicTusk subEpic = subEpics.get(subEpicId);
                historyManager.remove(subEpic.getId());
            }
        }
        epics.clear();
        subEpics.clear();
    }

    @Override
    public EpicTusk getEpic(int id) {
        if (epics.containsKey(id)) {
            historyManager.addTaskHistory(epics.get(id));
        } else {
            System.out.println("Эпик с ID " + id + " не существует. Добавление в историю невозможно.");
        }

        return epics.get(id);
    }

    @Override
    public ArrayList<EpicTusk> getAllEpic() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void addSubEpic(SubEpicTusk subEpic) {

        subEpic.setId(nextId++);
        subEpics.put(subEpic.getId(), subEpic);

        EpicTusk epic = epics.get(subEpic.getEpicID());
        epic.getEpicIds().add(subEpic.getId());


        epicCheckStatus(epic);

    }

    @Override
    public ArrayList<SubEpicTusk> getSubEpicTasks() {
        return new ArrayList<>(subEpics.values());
    }

    @Override
    public SubEpicTusk getSubEpic(int id) {
        if (subEpics.containsKey(id)) {
            historyManager.addTaskHistory(subEpics.get(id));
        } else {
            System.out.println("Подзадача Эпика с ID " + id + " не существует. Добавление в историю невозможно.");
        }
        return subEpics.get(id);
    }

    @Override
    public void updateSubEpic(SubEpicTusk subEpic) {
        subEpics.put(subEpic.getId(), subEpic);
    }

    @Override
    public void removeSubtaskById(int id, ArrayList<EpicTusk> epics) {
        SubEpicTusk subEpic = subEpics.remove(id);
        historyManager.remove(subEpic.getId());

        EpicTusk epic = epics.get(subEpic.getEpicID());
        epic.getEpicIds().remove(Integer.valueOf(id));
        epicCheckStatus(epic);
    }

    @Override
    public void removeAllSubEpic() {
        for (SubEpicTusk subEpic : subEpics.values()) {
            historyManager.remove(subEpic.getId());
        }
        subEpics.clear();

        for (EpicTusk epic : epics.values()) {
            epic.getEpicIds().clear();
            epicCheckStatus(epic);
        }
    }

    @Override
    public ArrayList<SubEpicTusk> getSubEpicsByEpicId(int id) {
        ArrayList<SubEpicTusk> subEpicsList = new ArrayList<>();
        for (SubEpicTusk subEpic : subEpics.values()) {
            if (subEpic.getEpicID() == id) {
                subEpicsList.add(subEpic);
            }
        }
        historyManager.addTaskHistory(subEpics.get(id));
        return subEpicsList;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public void addTaskHistory(Task task) {
        historyManager.addTaskHistory(task);
    }

    @Override
    public void removeTaskInHistory(int id) {
        historyManager.remove(id);
    }
}