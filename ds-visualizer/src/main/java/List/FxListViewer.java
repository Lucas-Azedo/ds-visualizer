package List;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FxListViewer<T> implements ListObserver<T> {
    private final Queue<ChangeEvent<T>> eventQueue = new LinkedList<>();
    private boolean isProcessing = false;

    private Stage stage;
    private FlowPane pane;

    public FxListViewer() {
        Platform.startup(() -> {
            stage = new Stage();
            pane = new FlowPane();
            stage.setTitle("Visualizador de Lista");
            stage.setScene(new Scene(pane, 400, 100));
            stage.show();
        });
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

        Platform.runLater(() -> {
            pane.getChildren().clear();
            for (int i = 0; i < event.list.size(); i++) {
                Label label = new Label(event.list.get(i).toString());
                label.setStyle("-fx-padding: 10; -fx-border-color: black;");
                if (i == event.index) {
                    label.setStyle(label.getStyle() + "-fx-background-color: yellow;");
                }
                pane.getChildren().add(label);
            }
        });

        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(e -> {
            isProcessing = false;
            processNext(); // chama o próximo
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
