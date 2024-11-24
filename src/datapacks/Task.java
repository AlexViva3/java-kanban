package datapacks;

public class Task { // Это задача
    private int id;
    private String name;//Название задачи
    private String description;// Описание задачи
    private StatusTask status;

    public Task(String name, String description, StatusTask status) {
        this.status = status;
        this.description = description;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public StatusTask getStatus() {
        return status;
    }

    public void setStatus(StatusTask status) {
        this.status = status;
    }
}
