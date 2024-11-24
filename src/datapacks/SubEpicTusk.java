package datapacks;

public class SubEpicTusk extends Task {
    public SubEpicTusk(String name, String description, StatusTask status) {
        super(name, description, status);
    }

    public int getSubId() {
        return subId;
    }

    public void setSubId(int subId) {
        this.subId = subId;
    }

    private int subId;
    // Это подзадача эпика
}
