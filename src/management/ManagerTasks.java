package management;
import datapacks.EpicTusk;
import datapacks.SubEpicTusk;
import datapacks.Task;
import datapacks.StatusTask;
import java.util.ArrayList;
import java.util.HashMap;

public class ManagerTasks {
    private  HashMap<Integer, Task> tasks = new HashMap<>();
    private  HashMap<Integer, EpicTusk> epics = new HashMap<>();
    private  HashMap<Integer, SubEpicTusk> subEpics = new HashMap<>();
    private int nextId = 0;

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
        epics.put(epic.getId(), epic);

    }

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

    public void updateEpic(EpicTusk epic) {
        epics.put(epic.getId(), epic);

        epicCheckStatus(epic);

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
        return epics.get(id);
    }

    public ArrayList<EpicTusk> getAllEpic() {
        return new ArrayList<>(epics.values());
    }

    public void addSubEpic(SubEpicTusk subEpic) {

        subEpic.setId(nextId++);
        subEpics.put(subEpic.getId(), subEpic);

        EpicTusk epic = epics.get(subEpic.getEpicID());
        epic.getEpicIds().add(subEpic.getId());


        epicCheckStatus(epic);

    }

    public ArrayList<SubEpicTusk> getSubEpicTasks() {
        return new ArrayList<>(subEpics.values());
    }

    public void updateSubEpic(SubEpicTusk subEpic) {
        subEpics.put(subEpic.getId(), subEpic);
    }

    public void removeSubtaskById(int id, ArrayList<EpicTusk> epics) {
        subEpics.remove(id);
        for (EpicTusk epic : epics) {
            if (epic.getEpicIds().contains(id)) {
                epic.getEpicIds().remove(Integer.valueOf(id));
                break;
            }
        }
        epicCheckStatus(epics.get(id));
    }

    public void removeAllSubEpic() {
            for (EpicTusk epic : epics.values()) {
                epic.getEpicIds().clear();
                epicCheckStatus(epic);
            }
            subEpics.clear();
        }

    public ArrayList<SubEpicTusk> getSubEpicsByEpicId(int id) {
        ArrayList<SubEpicTusk> subEpicsList = new ArrayList<>();
        for (SubEpicTusk subEpic : subEpics.values()) {
            if (subEpic.getEpicID() == id) {
                subEpicsList.add(subEpic);
            }
        }
        return subEpicsList;
    }
}