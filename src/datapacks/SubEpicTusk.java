package datapacks;

public class SubEpicTusk extends Task {
    public SubEpicTusk(String name, String description, StatusTask status, int epicID) {
        super(name, description, status);
        this.epicID = epicID;
    }

    public int getEpicID() {
        return epicID;
    }

    public void setEpicID(int epicID) {
        this.epicID = epicID;
    }

    private int epicID;

}