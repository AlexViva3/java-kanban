package management;
import datapacks.EpicTusk;
import datapacks.SubEpicTusk;
import datapacks.Task;
import datapacks.StatusTask;
import java.util.ArrayList;
import java.util.HashMap;

public class ManagerTasks {
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, EpicTusk> epics = new HashMap<>();
    HashMap<Integer, SubEpicTusk> subEpics = new HashMap<>();
    private int nextId = 1;

    public void addTask(Task task) {
        task.setId(nextId++);
        tasks.put(task.getId(), task);

    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void removeTask(Integer id) {
        tasks.remove(id);
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public Task takeTaskForId(int id) {
         return tasks.get(id);
    }

    public void addEpic(EpicTusk epic) {
        epic.setId(nextId++);
        tasks.put(epic.getId(), epic);

    }

    public void updateEpic(EpicTusk epic) {
        tasks.put(epic.getId(), epic);
        StatusTask status;

        if (epic.getStatus() == StatusTask.NEW) {
            status = StatusTask.NEW;
        } else if (epic.getStatus() == StatusTask.DONE) {
            status = StatusTask.DONE;
        } else {
            status = StatusTask.IN_PROGRESS;
        }
        epic.setStatus(status);

    }

    public void removeEpic(int id) {
        final EpicTusk epic = epics.remove(id);
        for (Integer subId : epic.getEpicIds()) {
            subEpics.remove(subId);
        }
    }

    public void removeAllEpic() {
        epics.clear();
        subEpics.clear();
    }

    public EpicTusk getEpic(int id) {
        if (tasks.containsKey(id)) {
            return epics.get(id);
        }

        return null;
    }
    public ArrayList<EpicTusk> getAllEpic() {
        return new ArrayList<>(epics.values());
    }

    public void addSubEpic(SubEpicTusk subEpic) {

        subEpic.setId(nextId++);
        subEpics.put(subEpic.getId(), subEpic);

        EpicTusk epic = epics.get(subEpic.getSubId());
        epic.getEpicIds().add(subEpic.getId());


    }

    public ArrayList<SubEpicTusk> getSubEpicTasks() {
        return new ArrayList<>(subEpics.values());
    }

    public void updateSubEpic(SubEpicTusk subEpic) {
        subEpics.put(subEpic.getId(), subEpic);
    }

    public void removeSubEpicTask(int id) {
        subEpics.remove(id);
    }
    public void removeAllSubEpic() {
        subEpics.clear();
    }

    public ArrayList<SubEpicTusk> getSubEpicsByEpicId(int id) {
        ArrayList<SubEpicTusk> subEpicsList = new ArrayList<>();
        for (SubEpicTusk subEpic : subEpics.values()) {
            if (subEpic.getSubId() == id) {
                subEpicsList.add(subEpic);
            }
        }
        return subEpicsList;
    }

}
