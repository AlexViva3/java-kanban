package history;

    public class Node<T> {
        T task;
        Node<T> prev;
        Node<T> next;

        public Node(T task) {
            this.task = task;
        }
    }

