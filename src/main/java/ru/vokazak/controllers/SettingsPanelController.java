package ru.vokazak.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import ru.vokazak.config.Settings;
import ru.vokazak.exceptions.SettingsException;
import ru.vokazak.jda.JdaFactory;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
@FxmlView("settingsPanel.fxml")
public class SettingsPanelController implements Initializable {

    private final Settings settings;
    private final JdaFactory jdaFactory;
    private final MessagePanelController messagePanelController;
    private final MusicPlayerController musicPlayerController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tokenField.setText(settings.getToken());
        musicFolderField.setText(settings.getMusicFolder());
    }

    @FXML
    private TextField tokenField;

    @FXML
    private Button applyTokenButton;
    public void onTokenButtonClicked() {
        settings.setToken(tokenField.getText());

        try {
            settings.saveSettings();
        } catch (SettingsException ignored) {}

        jdaFactory.getJda().shutdown();
        jdaFactory.initJda(tokenField.getText());

        messagePanelController.renew();
        musicPlayerController.renew();
    }

    @FXML
    private TextField musicFolderField;

    @FXML
    private Button applyMusicFolderButton;
    public void onMusicFolderButtonClicked() {
        settings.setMusicFolder(musicFolderField.getText());
        musicFolderField.setText(musicFolderField.getText());

        try {
            settings.saveSettings();
        } catch (SettingsException ignored) {}

        messagePanelController.renew();
        musicPlayerController.renew();
    }


}
