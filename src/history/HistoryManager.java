package history;

import datapacks.Task;

import java.util.List;

public interface HistoryManager {
    void addTaskHistory(Task task);// Добавить задачу в историю
    List<Task> getHistory();
}
