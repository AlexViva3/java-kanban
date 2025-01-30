package history;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskCycler<T> {
    private Node<T> first;
    private Node<T> last;
    private Node<T> current;
    private final Map<T, Node<T>> nodeMap = new HashMap<>();

    public void linkLast(T task) {
        Node<T> newNode = new Node<>(task);
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

    public void removeNode(T task) {
        Node<T> node = nodeMap.get(task);
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

    public List<T> getHistory() {
        List<T> history = new ArrayList<>();
        if (first == null) {
            return history;
        }

        Node<T> current = first;
        do {
            history.add(current.task);
            current = current.next;
        } while (current != first);

        return history;
    }

    public boolean contains(T task) {
        return nodeMap.containsKey(task);
    }
}
