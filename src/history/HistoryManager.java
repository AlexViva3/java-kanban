package history;

import datapacks.EpicTusk;
import datapacks.SubEpicTusk;
import datapacks.Task;

import java.util.List;

public interface HistoryManager {
    void addTaskHistory(Task task);
    void addEpicHistory(EpicTusk epic);
    void addSubEpicHistory(SubEpicTusk subEpic);// Добавить задачу в историю
    List<Task> getHistory();
}
