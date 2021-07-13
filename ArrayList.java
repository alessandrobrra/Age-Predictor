public class ArrayList <T> implements List<T>{
    T[] arr;
    int size;
    @SuppressWarnings("hiding")
    private class ListIterator<T> implements Iterator<T>{
        private int nextItem;

        public ListIterator(){
            nextItem = 0;
        }
        @Override
        public boolean hasNext() {
            return nextItem < size;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T next() {
            return (T) arr[nextItem++];
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayList(){
        size = 0;
        arr = (T[]) new Object[10];
    }



    @SuppressWarnings("unchecked")
    private void growArray(){
        T[] newArr = (T[]) new Object[arr.length*2];
        for (int i = 0; i < arr.length; i++) {
            newArr[i] = arr[i];
        }
        arr = newArr;
    }
    @Override
    public void add(T item){
        if(size==arr.length){
            growArray();
        }
        arr[size++] = item;
    }

    @Override
    public void add(T item, int pos) throws Exception {
        if(pos<0 || pos>size-1){
            throw new Exception("Index out of bounds");
        }
        if(size==arr.length){
            growArray();
        }
        for (int i = size; i>pos; i--){
            arr[i] = arr[i-1];
        }
        arr[pos] = item;
        size++;
    }

    @Override
    public T get(int pos) throws Exception {
        if (pos < 0 || pos > size - 1) {
            throw new Exception("Index out of bounds");
        }
        return arr[pos];
    }

    @Override
    public T remove(int pos) throws Exception {
        if(pos<0 || pos>size-1){
            throw new Exception("Index out of bounds");
        }
        T item = arr[pos];
        for (int i = pos; i < size-1; i++) {
            arr[i] = arr[i+1];
        }
        --size;
        return item;
    }

    @Override
    public int size() {
        return size;
    }

    public Iterator<T> iterator() {
        return new ListIterator<T>();
    }
}
