package datapacks;

public class SubEpicTusk extends Task {
    public SubEpicTusk(String name, String description, StatusTask status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int d) {
        this.epicId = epicId;
    }

    private int epicId;

    public TaskType getType() {
        return TaskType.SUBTASK;  // Для Task
    }
}