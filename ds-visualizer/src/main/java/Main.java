import List.oArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        oArrayList<Integer> list1 = new oArrayList<>();
        oArrayList<Integer> list2 = new oArrayList<>();

        for (int i = 0; i < 1000000; i++) {
            list1.add(i);
            i = i + 25;
        }
    }
}
