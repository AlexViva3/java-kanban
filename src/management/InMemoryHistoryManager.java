package management;

import datapacks.Task;
import history.HistoryManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InMemoryHistoryManager implements HistoryManager {

    private static final int HISTORY_LIMIT = 10;

    private Node<Task> first;
    private Node<Task> last;
    private Node<Task> current;
    private final Map<Task, Node<Task>> nodeMap = new HashMap<>();

     private static class Node<TaskT> {
        TaskT task;
        Node<TaskT> prev;
        Node<TaskT> next;

        public Node(TaskT task) {
            this.task = task;
        }
    }

    @Override
    public void addTaskHistory(Task task) {

        if (nodeMap.containsKey(task)) {
            removeNode(task);
        }

        linkLast(task);

        if (nodeMap.size() > HISTORY_LIMIT) {
            removeNode(first.task);
        }
    }

    @Override
    public void remove(int id) {
        Task taskToRemove = null;

        for (Task task : nodeMap.keySet()) {
            if (task.getId() == id) {
                taskToRemove = task;
                break;
            }
        }

        if (taskToRemove != null) {
            removeNode(taskToRemove);
        }
    }

    @Override
    public List<Task> getHistory() {

        List<Task> history = new ArrayList<>();

        if (first == null) {
            return history;
        }

        Node<Task> current = first;

        do {
            history.add(current.task);
            current = current.next;
        } while (current != first);

        return history;
    }

    @Override
    public void linkLast(Task task) {

        Node<Task> newNode = new Node<>(task);
        nodeMap.put(task, newNode);

        if (last == null) {
            first = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
        }
        last = newNode;

        last.next = first;
        first.prev = last;
    }

    @Override
    public void removeNode(Task task) {

        Node<Task> node = nodeMap.get(task);
        if (node == null) return;

        if (node == current) {
            current = node.next != node ? node.next : null;

        }

        node.prev.next = node.next;
        node.next.prev = node.prev;

        if (node == first) first = node.next;
        if (node == last) last = node.prev;

        nodeMap.remove(task);
    }

    @Override
    public boolean contains(Task task) {

        return nodeMap.containsKey(task);
    }

    @Override
    public void removeTask(Task task) {

        removeNode(task);
    }

}