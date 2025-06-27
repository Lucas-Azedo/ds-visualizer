package List;

import java.util.List;

public class ConsoleListViewer<T> implements ListObserver<T> {
    @Override
    public void onChanged(List<T> list, int index, String action) {
        System.out.println("\n[Lista modificada - ação: " + action + " no índice " + index + "]");

        for (int i = 0; i < list.size(); i++) {
            if (i == index) {
                System.out.printf("[%s] ", list.get(i));
            } else {
                System.out.print(list.get(i) + " ");
            }
        }
            System.out.println();
    }
}

