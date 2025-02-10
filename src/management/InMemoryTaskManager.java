package management;
import datapacks.EpicTusk;
import datapacks.SubEpicTusk;
import datapacks.Task;
import datapacks.StatusTask;
import history.HistoryManager;
import history.TaskHistoryCycler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, EpicTusk> epics = new HashMap<>();
    private final HashMap<Integer, SubEpicTusk> subEpics = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    private final TaskHistoryCycler<Task> taskCycler = new TaskHistoryCycler<>();

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
        tasks.remove(id);
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public void updateTask(Task task) {

        tasks.put(task.getId(), task);

        historyManager.addTaskHistory(task);
    }

    @Override
    public Task takeTaskForId(int id) {
        if (tasks.containsKey(id)) {
            historyManager.addTaskHistory(tasks.get(id)); // Добавляем задачу в историю
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
        final EpicTusk epic = epics.remove(id);
        for (Integer subId : epic.getEpicIds()) {
            subEpics.remove(subId);
        }
    }

    @Override
    public void removeAllEpic() {
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
        subEpics.remove(id);
        EpicTusk epic = epics.get(id); // Используйте epics.get(id) вместо epics.get(id)
        if (epic != null) {
            epic.getEpicIds().remove(Integer.valueOf(id));
            epicCheckStatus(epic);
        }
    }

    @Override
    public void removeAllSubEpic() {
        for (EpicTusk epic : epics.values()) {
            epic.getEpicIds().clear();
            epicCheckStatus(epic);
        }
        subEpics.clear();
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
        if (taskCycler.contains(task)) {
            taskCycler.removeNode(task);
        }
        taskCycler.linkLast(task);
    }

    @Override
    public void remove(int id) {
        Task taskToRemove = null;
        for (Task task : taskCycler.getHistory()) {
            if (task.getId() == id) {
                taskToRemove = task;
                break;
            }
        }
        if (taskToRemove != null) {
            taskCycler.removeNode(taskToRemove);
        }
    }
}