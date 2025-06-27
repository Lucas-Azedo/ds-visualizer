import List.ConsoleListViewer;
import List.ObservableList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ObservableList<Integer> list = new ObservableList<Integer>();

        list.add(10); // render automático
        list.add(20);
        list.set(1, 99);
    }
}
