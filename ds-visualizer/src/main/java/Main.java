import List.ConsoleListViewer;
import List.ObservableList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ObservableList<Integer> list = new ObservableList<Integer>();

        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        list.add(50);
        list.add(60);
        list.set(1, 15);
        list.set(2, 25);
    }
}
