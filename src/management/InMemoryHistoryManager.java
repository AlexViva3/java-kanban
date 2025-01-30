package management;

import datapacks.Task;
import history.HistoryManager;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int HISTORY_LIMIT = 10; // Лимит на 10 задач
    private final List<Task> history = new ArrayList<>();


    @Override
    public void addTaskHistory(Task task) {
        if (history.size() >= HISTORY_LIMIT) history.removeFirst();

        history.add(task);


    }

    @Override
    public void remove(int index) {

    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }
}
