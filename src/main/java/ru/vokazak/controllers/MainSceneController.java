package ru.vokazak.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.dv8tion.jda.api.JDA;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.springframework.stereotype.Component;
import ru.vokazak.config.SpringContext;
import ru.vokazak.jda.JdaFactory;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

@Component
@FxmlView("home.fxml")
public class MainSceneController implements Initializable {

    private Map<String, Pane> paneMap;

    private final JDA jda;

    @FXML
    private HBox statsBox;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private VBox actionBox;

    public MainSceneController(JdaFactory jdaFactory) {
        this.jda = jdaFactory.getJda();
    }

    @FXML
    private void onHideButtonClicked() {
        ((Stage) mainPane.getScene().getWindow()).setIconified(true);
    }

    @FXML
    private void onExitButtonClicked() {
        jda.shutdown();
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException ignored) {}

        Platform.exit();
    }

    @FXML
    private void onMessageButtonClicked() {
        setNewPane("messagePanel");
    }

    @FXML
    private void onMusicButtonClicked() {
        setNewPane("musicPanel");
    }

    @FXML
    private void onSettingsButtonClicked() {
        setNewPane("settingsPanel");
    }

    private void setNewPane(String paneName) {
        if (!actionBox.getChildren().isEmpty()) {
            actionBox.getChildren().clear();
        }
        actionBox.getChildren().add(paneMap.get(paneName));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FxWeaver fxWeaver = SpringContext.getContext().getBean(FxWeaver.class);

        paneMap = new HashMap<>();
        paneMap.put("musicPanel", fxWeaver.loadView(MusicPlayerController.class));
        paneMap.put("messagePanel", fxWeaver.loadView(MessagePanelController.class));
        paneMap.put("settingsPanel", fxWeaver.loadView(SettingsPanelController.class));

        statsBox.getChildren().add(fxWeaver.loadView(TimeController.class));

        setNewPane("messagePanel");

    }
}
