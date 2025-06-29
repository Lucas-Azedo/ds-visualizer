package List;

import java.util.ArrayList;
import java.util.Collection;

public class oArrayList<T> extends ArrayList<T> {

    private final ArrayList<ListObserver<T>> observers = new ArrayList<>();

    public oArrayList() {
        super();
        this.addObserver(new FxListViewer<T>());
    }

    public oArrayList(int initialCapacity) {
        super(initialCapacity);
        this.addObserver(new FxListViewer<T>());
    }

    public oArrayList(Collection<? extends T> c) {
        super(c);
        this.addObserver(new FxListViewer<T>());
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
