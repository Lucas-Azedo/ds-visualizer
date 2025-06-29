package List;

import Ui.FxUiWindow;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FxListViewer<T> implements ListObserver<T> {
    private final Queue<ChangeEvent<T>> eventQueue = new LinkedList<>();
    private boolean isProcessing = false;

    private final FxUiWindow<T> window;

    public FxListViewer() {
        this.window = new FxUiWindow<>("Visualizador de Lista", 640, 480);
    }

    @Override
    public void onChanged(List<T> list, int index, String action) {
        eventQueue.offer(new ChangeEvent<>(List.copyOf(list), index, action));
        processNext();
    }

    private void processNext() {
        if (isProcessing || eventQueue.isEmpty()) return;

        isProcessing = true;
        ChangeEvent<T> event = eventQueue.poll();

        window.update(event.list, event.index);

        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(e -> {
            isProcessing = false;
            processNext();
        });
        delay.play();
    }

    private static class ChangeEvent<T> {
        List<T> list;
        int index;
        String action;

        ChangeEvent(List<T> list, int index, String action) {
            this.list = list;
            this.index = index;
            this.action = action;
        }
    }
}
