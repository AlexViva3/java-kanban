package datapacks;

import java.util.ArrayList;

public class EpicTusk extends Task {
    private ArrayList<Integer> subIDs = new ArrayList<>();

    public EpicTusk(String name, String description, StatusTask status) {
        super(name, description, status);
    }

    public ArrayList<Integer> getEpicIds() {
        return subIDs;
    }

    public void setEpicIds(ArrayList<Integer> subIDs) {
        this.subIDs = subIDs;
    }
    // это эпик
}

