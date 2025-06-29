package Ui;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

public class FxUiWindow<T> {
    private static boolean fxStarted = false;

    private Stage stage;
    private FlowPane pane;

    public FxUiWindow(String title, int width, int height) {
        startJavaFxIfNeeded();

        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            pane = new FlowPane();
            pane.getStyleClass().add("flow-pane");

            stage = new Stage();
            Scene scene = new Scene(pane, width, height);

            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
            latch.countDown();;
        });

        try {
            latch.await(); // Garante que a janela seja construída antes de usar
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(List<T> list, int highlightIndex) {
        Platform.runLater(() -> {
            pane.getChildren().clear();
            for (int i = 0; i < list.size(); i++) {
                Label label = new Label(String.valueOf(list.get(i)));
                label.setStyle(
                        "-fx-background-color: #1d2327;" +
                        "-fx-text-fill: white;" +
                        "-fx-padding: 10px;" +
                        "-fx-border-color: pink;" +
                        "-fx-border-width: 2;" +
                        "-fx-pref-width: 60px;" +
                        "-fx-font-family: monospace;" +
                        "-fx-alignment: center;" +
                        "-fx-font-size: 14px;");
                if (i == highlightIndex) {
                    label.setStyle(label.getStyle() + "-fx-background-color: #2271b1");
                }
                pane.getChildren().add(label);
            }
        });
    }

    private void startJavaFxIfNeeded() {
        if (!fxStarted) {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.startup(() -> {
                fxStarted = true;
                latch.countDown();
            });
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
