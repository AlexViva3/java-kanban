package datapacks;

public class SubEpicTusk extends Task {
    public SubEpicTusk(String name, String description, StatusTask status) {
        super(name, description, status);
    }

    public int getEpicID() {
        return epicID;
    }

    public void setSubId(int subId) {
        this.epicID = epicID;
    }

    private int epicID;
    // Это подзадача эпика
}