package List;

import java.util.List;

public interface ListObserver<T> {
    void onChanged(List<T> list, int modifiedIndex, String action);
}

