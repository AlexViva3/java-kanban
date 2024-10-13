import java.util.ArrayList;
import java.util.HashMap;

public class ManagerTasks {
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, EpicTusk> epics = new HashMap<>();
    HashMap<Integer, SubEpicTusk> subEpics = new HashMap<>();
    int nextId = 1;

    public void addTask(Task task) {
        task.id = nextId++;
        tasks.put(task.id, task);
        StatusTask status = StatusTask.NEW;

    }

    public Task getTask(int id) {
        return tasks.get(id);
    }
    public ArrayList<Task> getAllTask() {
        ArrayList<Task> taskId = new ArrayList<>();
        for (Task task : tasks.values()) {
            Task newTask = tasks.get(task.id);
            taskId.add(newTask);
        }
return taskId;
    }

    public void removeTask(Integer id) {
        tasks.remove(id);
    }

    public void updateTask(Task task, StatusTask status) {
        tasks.put(task.id, task);
        status = status;
    }

    public void removeEpicTask(int id) {
        epics.remove(id);
        subEpics.remove(id);

    }

    public void addEpic(EpicTusk epic, StatusTask status) {
    epic.id = nextId++;
    tasks.put(epic.id, epic);

    }

    public void updateEpic(EpicTusk epic) {
        epics.put(epic.id, epic);
        for (Integer id : epic.epicIds) {
            if (SubEpicTusk.status == StatusTask.NEW) {
            StatusTask status = StatusTask.NEW;
            } else if (SubEpicTusk.status == StatusTask.DONE) {
                StatusTask status = StatusTask.DONE;
            } else {
                StatusTask status = StatusTask.IN_PROGRESS;
            }
        }
    }
    public Task getEpicTask(int id) {
        if (tasks.containsKey(id)) {
         return epics.get(id);
        } else {
            System.out.println("Такой задачи нет");
        }
        return null;
    }

    public void addSubEpic(SubEpicTusk subEpic) {

        subEpic.id = nextId++;
        subEpics.put(subEpic.id, subEpic);

        EpicTusk epic = epics.get(subEpic.subId);
        epic.epicIds.add(subEpic.id);

        StatusTask status = StatusTask.NEW;

    }


    public SubEpicTusk getSubEpic(int id) {
        return subEpics.get(id);
    }

    public void removeSubEpicTask(int id) {
        subEpics.remove(id);
    }
}
