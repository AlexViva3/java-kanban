package management;

import datapacks.EpicTusk;
import datapacks.SubEpicTusk;
import datapacks.Task;
import history.HistoryManager;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int HISTORY_LIMIT = 10; // Лимит на 10 задач
    private final List<Task> history = new ArrayList<>();


    @Override
    public void addTaskHistory(Task task) {
        if (history.size() >= HISTORY_LIMIT) {
            history.remove(0);
        }


    }

    @Override
    public void addEpicHistory(EpicTusk epic) {
        if (history.size() >= HISTORY_LIMIT) {
            history.remove(0);
        }
        history.add(epic);
    }

    @Override
    public void addSubEpicHistory(SubEpicTusk subEpic) {
        if (history.size() >= HISTORY_LIMIT) {
            history.remove(0);
        }
        history.add(subEpic);
    }


    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }
}
