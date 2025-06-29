package Ui;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

public class FxUiWindow<T> {
    private static boolean fxStarted = false;

    private Stage stage;
    private ScrollPane scrollPane;
    private FlowPane pane;
    private Pane canvas;

    private final int canvasWidth = 1600;
    private final int canvasHeight = 900;
    private final int blockWidth = 80; // 60 + 10 gap

    public FxUiWindow(String title, int width, int height) {
        startJavaFxIfNeeded();

        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            pane = new FlowPane();
            pane.setHgap(10);
            pane.setVgap(10);
            pane.setPrefWrapLength(800); // Largura máxima antes de quebrar linha

            canvas = new Pane();
            canvas.setPrefSize(canvasWidth, canvasHeight);
            canvas.getChildren().add(pane);

            scrollPane = new ScrollPane(canvas);
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            scrollPane.setPannable(true);

            Scene scene = new Scene(scrollPane, width, height);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());

            stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();

            scrollPane.setHvalue(0.5);
            scrollPane.setVvalue(0.5);

            latch.countDown();
        });

        try {
            latch.await();
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
                                "-fx-pref-width: 70px;" +
                                "-fx-font-family: monospace;" +
                                "-fx-alignment: center;" +
                                "-fx-font-size: 14px;");
                if (i == highlightIndex) {
                    label.setStyle(label.getStyle() + "-fx-background-color: #2271b1;");
                }
                pane.getChildren().add(label);
            }

            // Centraliza o FlowPane dentro do canvas
            double contentWidth = Math.min(pane.getPrefWrapLength(), list.size() * blockWidth);
            double x = (canvasWidth - contentWidth) / 2.0;
            double y = (canvasHeight) / 2.0;
            pane.setLayoutX(Math.max(0, x));
            pane.setLayoutY(Math.max(0, y));
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
