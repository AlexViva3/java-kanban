package datapacks;

import java.util.ArrayList;

public class EpicTusk extends Task {
    private ArrayList<Integer> subIds = new ArrayList<>();

    public EpicTusk(String name, String description, StatusTask status) {
        super(name, description, status);
    }

    public ArrayList<Integer> getEpicIds() {
        return subIds;
    }

    public void setEpicIds(ArrayList<Integer> subIds) {
        this.subIds = subIds;
    }

    public TaskType getType() {
        return TaskType.EPIC;  // Для Task
    }
}

