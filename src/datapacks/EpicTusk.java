package datapacks;

import java.util.ArrayList;

public class EpicTusk extends Task {
    private ArrayList<Integer> epicIds = new ArrayList<>();

    public EpicTusk(String name, String description, StatusTask status) {
        super(name, description, status);
    }

    public ArrayList<Integer> getEpicIds() {
        return epicIds;
    }

    public void setEpicIds(ArrayList<Integer> epicIds) {
        this.epicIds = epicIds;
    }
    // это эпик
}
