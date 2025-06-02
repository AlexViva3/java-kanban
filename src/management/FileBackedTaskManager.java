package management;

import datapacks.*;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private final Path file;

    public FileBackedTaskManager(Path file) {
        this.file = file;
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        Path path = file.toPath();
        FileBackedTaskManager manager = new FileBackedTaskManager(path);
        manager.load();
        return manager;
    }

    public String toString(Task task) {
        String[] fields = {
                String.valueOf(task.getId()),
                task.getType().name(),
                task.getName(),
                task.getStatus().name(),
                task.getDescription(),
                task instanceof SubEpicTusk ?
                        String.valueOf(((SubEpicTusk) task).getEpicID()) : ""
        };

        return String.join(",", fields);
    }

    public Task fromString(String value) {

        if (value == null || value.isEmpty()) {
            return null;
        }

        String[] fields = value.split(",");
        if (fields.length < 5) {
            throw new IllegalArgumentException("Некорректная строка задачи: " + value);
        }

        TaskType type = TaskType.valueOf(fields[1]);
        String name = fields[2];
        StatusTask status = StatusTask.valueOf(fields[3]);
        String description = fields[4];

        switch (type) {
            case TASK:
                Task task = new Task(name, description, status);
                addTask(task);
                return task;

            case EPIC:
                EpicTusk epic = new EpicTusk(name, description, status);
                addEpic(epic);
                return epic;

            case SUBTASK:
                if (fields.length < 6) {
                    throw new IllegalArgumentException("Для подзадачи не указан ID эпика: " + value);
                }
                int epicId = Integer.parseInt(fields[5]);
                SubEpicTusk subtask = new SubEpicTusk(name, description, status, epicId);
                addSubEpic(subtask);
                return subtask;

            default:
                throw new IllegalArgumentException("Неизвестный тип задачи: " + type);

        }

    }

    private void save() {
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            for (Task task : getAllTasks()) {
                writer.write(task.toString());
                writer.newLine();
            }
            for (EpicTusk epic : getAllEpic()) {
                writer.write(epic.toString());
                writer.newLine();
            }

            for (SubEpicTusk subEpic : getSubEpicTasks()) {
                writer.write(subEpic.toString());
                writer.newLine();
            }
        } catch (IOException e) {

            System.err.println("Ошибка сохранения: " + e.getMessage()); // Использую System.err для вывода ошибок, так как вычитал, что он лучше для вывода ошибок :))
        }
    }

    public void load() {

        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = fromString(line);
                if (task == null) continue;

                // Используем публичные методы добавления, а не геттеры!
                if (task instanceof EpicTusk) {
                    addEpic((EpicTusk) task);  // Используем метод addEpic()
                } else if (task instanceof SubEpicTusk) {
                    addSubEpic((SubEpicTusk) task);  // Используем метод addSubEpic()
                } else {
                    addTask(task);  // Используем метод addTask()
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка сохранения: " + e.getMessage()); // Использую System.err для вывода ошибок, так как вычитал, что он лучше для вывода ошибок :))
        }
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addSubEpic(SubEpicTusk subEpic) {
        super.addSubEpic(subEpic);
        save();
    }

    @Override
    public void addEpic(EpicTusk epicTusk) {
        super.addEpic(epicTusk);
        save();
    }
}