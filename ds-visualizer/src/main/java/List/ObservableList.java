package List;

import java.util.ArrayList;
import java.util.Collection;

public class ObservableList<T> extends ArrayList<T> {

    private final ArrayList<ListObserver<T>> observers = new ArrayList<>();

    public ObservableList() {
        super();
        this.addObserver(new ConsoleListViewer<T>());
    }

    public ObservableList(int initialCapacity) {
        super(initialCapacity);
        this.addObserver(new ConsoleListViewer<T>());
    }

    public ObservableList(Collection<? extends T> c) {
        super(c);
        this.addObserver(new ConsoleListViewer<T>());
    }

    public void addObserver(ListObserver<T> observer) {
        observers.add(observer);
    }

    private void notifyObservers(int index, String action) {
        for (ListObserver<T> obs : observers) {
            obs.onChanged(this, index, action);
        }
    }

    @Override
    public boolean add(T element) {
        boolean added = super.add(element);
        if (added) notifyObservers(size() - 1, "add");
        return added;
    }

    @Override
    public T set(int index, T element) {
        T old = super.set(index, element);
        notifyObservers(index, "set");
        return old;
    }

    @Override
    public T remove(int index) {
        T removed = super.remove(index);
        notifyObservers(index, "remove");
        return removed;
    }
}
