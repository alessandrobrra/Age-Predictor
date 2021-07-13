public class LinkedList<T> implements List<T> {
    @SuppressWarnings("hiding")
    private class Node<T> {
        T data;
        Node<T> next;

        public Node(T value) {
            data = value;
            next = null;
        }
    }


    @SuppressWarnings("hiding")
    private class LinkedIterator<T> implements Iterator<T> {
        private Node<T> node;

        @SuppressWarnings("unchecked")
        public LinkedIterator() {
            node = (Node<T>) head;
        }

        @Override
        public boolean hasNext() {
            if (node != null)
                return true;
            else
                return false;
        }

        @Override
        public T next() {
            Node<T> prev = node;
            node = node.next;
            return prev.data;
        }

    }

    private int size;
    private Node<T> head;
    public LinkedList () {
        size = 0;
        head = null;
    }


    @Override
    public void add(T item) {
        if (head == null) {
            head = new Node<T>(item);
        } else {
            Node<T> node = head;
            for (int i = 0; i < size-1; i++)
                node = node.next;
            Node<T> newNode = new Node<T>(item);
            node.next = newNode;
        }
        ++size;
    }
    @Override
    public void add(T item, int pos) throws Exception {
        if (pos < 0 || pos > size)
            throw new Exception("List index out of bounds");
        if (pos == 0) {
            Node<T> node = new Node<T>(item);
            node.next = head;
            head = node;
            ++size;
        } else {
            Node<T> prev = head;
            for (int i = 0; i < pos-1; i++)
                prev = prev.next;
            Node<T> newNode = new Node<T>(item);
            newNode.next = prev.next;
            prev.next = newNode;
            ++size;
        }
    }

    @Override
    public T get(int pos) throws Exception {
        if (pos < 0 || pos >= size)
            throw new Exception("List index out of bounds");
        Node<T> curr = head;
        for (int i = 0; i < pos; i++)
            curr = curr.next;
        return curr.data;
    }

    @Override
    public T remove(int pos) throws Exception {
        if (pos < 0 || pos > size)
            throw new Exception("List index out of bounds");

        if (pos == 0) {
            Node<T> node = head;
            head = head.next;
            --size;
            return node.data;
        }

        // Not at head removal

        Node<T> prev = head;
        for (int i = 0; i < pos-1; i++) {
            prev = prev.next;
        }
        Node<T> node = prev.next;
        prev.next = node.next;
        --size;
        return node.data;
    }


    @Override
    public int size() {
        return size;
    }


    public Iterator<T> iterator() {
        return new LinkedIterator<T>();
    }

}
