package history;
import datapacks.Task;
import java.util.List;

    public interface HistoryManager {

    void addTaskHistory(Task task);

    List<Task> getHistory();


}