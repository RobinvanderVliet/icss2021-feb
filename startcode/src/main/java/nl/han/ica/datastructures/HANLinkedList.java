package nl.han.ica.datastructures;

public class HANLinkedList<T> implements IHANLinkedList<T> {
    private Node<T> headNode = null;
    private int size = 0;

    @Override
    public void addFirst(T value) {
        headNode = new Node<>(value, headNode);
        size++;
    }

    @Override
    public void clear() {
        headNode = null;
        size = 0;
    }

    @Override
    public void insert(int index, T value) {
        if (index < 0 || index > getSize()) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            addFirst(value);
            return;
        }

        Node<T> node = headNode;

        for (int i = 0; i < index - 1; i++) {
            node = node.nextNode;
        }

        node.nextNode = new Node<>(value, node.nextNode);
        size++;
    }

    @Override
    public void delete(int pos) {
        if (pos < 0 || pos >= getSize()) {
            throw new IndexOutOfBoundsException();
        }

        if (pos == 0) {
            removeFirst();
            return;
        }

        Node<T> node = headNode;

        for (int i = 0; i < pos - 1; i++) {
            node = node.nextNode;
        }

        node.nextNode = node.nextNode.nextNode;
        size--;
    }

    @Override
    public T get(int pos) {
        if (pos < 0 || pos >= getSize()) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> node = headNode;

        for (int i = 0; i < pos; i++) {
            node = node.nextNode;
        }

        return node.data;
    }

    @Override
    public void removeFirst() {
        if (headNode == null) {
            throw new IndexOutOfBoundsException();
        }

        headNode = headNode.nextNode;
        size--;
    }

    @Override
    public T getFirst() {
        return get(0);
    }

    @Override
    public int getSize() {
        return size;
    }

    /**
     * Simple struct to contain a node.
     * @param <T> The selected data type.
     */
    private static class Node<T> {
        /**
         * The data stored in the node.
         */
        public T data;

        /**
         * The next node in the list.
         */
        public Node<T> nextNode;

        /**
         * Initialize an instance of a node.
         * @param data The data stored in the node.
         * @param nextNode The next node in the list.
         */
        public Node(T data, Node<T> nextNode) {
            this.data = data;
            this.nextNode = nextNode;
        }
    }
}
