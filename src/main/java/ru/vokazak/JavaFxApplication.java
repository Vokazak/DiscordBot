package ru.vokazak;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.rgielen.fxweaver.core.FxWeaver;
import ru.vokazak.config.SpringContext;
import ru.vokazak.controllers.MainSceneController;

public class JavaFxApplication extends Application {

    private double xOffset = 0.0;
    private double yOffset = 0.0;

    @Override
    public void start(Stage stage) {
        FxWeaver fxWeaver = SpringContext.getContext().getBean(FxWeaver.class);
        Parent root = fxWeaver.loadView(MainSceneController.class);

        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });


        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    @Override
    public void stop() {
        Platform.exit();
    }

}
