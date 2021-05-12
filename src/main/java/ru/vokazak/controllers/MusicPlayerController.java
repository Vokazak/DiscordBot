package ru.vokazak.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import net.rgielen.fxweaver.core.FxmlView;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.springframework.stereotype.Component;
import ru.vokazak.jda.JdaFactory;
import ru.vokazak.player.PlayerManager;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@FxmlView("musicPanel.fxml")
public class MusicPlayerController implements Initializable {

    private final JDA jda;

    private VoiceChannel currentVoiceChannel;
    private TextChannel currentTextChannel;

    private static final String MUSIC_PATH = "/F:/Music/DiscordSamples/";

    private final PlayerManager playerManager = PlayerManager.getInstance();

    public MusicPlayerController(JdaFactory jdaFactory) {
        this.jda = jdaFactory.getJda();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        voiceChannelChoiceBox.setItems(getVoiceChannelChoiceBoxList());
        voiceChannelChoiceBox.setValue(voiceChannelChoiceBox.getItems().get(0));
        currentVoiceChannel = jda.getVoiceChannels().get(0);

        tracksChoiceBox.setItems(getTracksChoiceBoxList());
        tracksChoiceBox.setValue(tracksChoiceBox.getItems().get(0));

        initMusicButtonPane();

        textChannelChoiceBox.setItems(getTextChannelChoiceBoxList());
        textChannelChoiceBox.setValue(textChannelChoiceBox.getItems().get(0));
        currentTextChannel = jda.getTextChannels().get(0);
    }


    @FXML
    private ChoiceBox<String> textChannelChoiceBox;

    @FXML
    private GridPane musicButtonPane;
    Map<Integer, Button> buttonMap = new HashMap<>();

    @FXML
    private ChoiceBox<String> tracksChoiceBox;

    @FXML
    private ChoiceBox<String> voiceChannelChoiceBox;


    @FXML
    private void onConnectButtonClicked() {
        currentVoiceChannel = jda.getVoiceChannelsByName(voiceChannelChoiceBox.getValue(), true).get(0);

        AudioManager audioManager;
        try {
            audioManager = jda
                    .getGuildChannelById(currentVoiceChannel.getId())
                    .getGuild()
                    .getAudioManager();
        } catch (NullPointerException e) {
            System.out.println("error");
            return;
        }

        if (audioManager.isConnected()) {
            System.out.println("already connected");
            return;
        }
        audioManager.openAudioConnection(currentVoiceChannel);

    }

    @FXML
    private void onDisconnectButtonClicked() {
        AudioManager audioManager;
        try {
            audioManager = jda
                    .getGuildChannelById(currentVoiceChannel.getId())
                    .getGuild()
                    .getAudioManager();
        } catch (NullPointerException e) {
            System.out.println("error");
            return;
        }

        if (!audioManager.isConnected()) {
            System.out.println("already disconnected");
            return;
        }

        audioManager.closeAudioConnection();
    }


    private ObservableList<String> getVoiceChannelChoiceBoxList() {
        List<String> voiceChannelList = new ArrayList<>();
        for (VoiceChannel c: jda.getVoiceChannels()) {
            String channelName = c.toString().substring(3).replaceAll("\\([0-9]*\\)", "");
            voiceChannelList.add(channelName);
        }

        return FXCollections.observableList(voiceChannelList);
    }

    private ObservableList<String> getTextChannelChoiceBoxList() {
        List<String> textChannelList = new ArrayList<>();
        for (TextChannel c: jda.getTextChannels()) {
            String channelName = c.toString().substring(3).replaceAll("\\([0-9]*\\)", "");
            textChannelList.add(channelName);
        }

        return FXCollections.observableList(textChannelList);
    }


    private ObservableList<String> getTracksChoiceBoxList() {
        return FXCollections.observableList(
                Stream.of(new File(MUSIC_PATH).listFiles())
                    .filter(file -> !file.isDirectory())
                    .map(File::getName)
                    .collect(Collectors.toList())
                );
    }

    private void initMusicButtonPane() {
        int columns = musicButtonPane.getColumnCount();
        int rows = musicButtonPane.getRowCount();

        int z = 1;
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {

                VBox gridPaneElement = new VBox();
                gridPaneElement.setAlignment(Pos.CENTER);

                Label label = new Label("Num " + z);
                label.setFont(Font.font(16));

                Button b = new Button();
                b.setText("-");
                b.setStyle("-fx-background-color: #EBE8F9");
                b.setMaxWidth(110.0);
                b.setMaxHeight(100.0);
                b.setFont(Font.font(16));

                b.setOnAction(e ->
                    b.setText(tracksChoiceBox.getValue())
                );

                gridPaneElement.getChildren().add(label);
                gridPaneElement.getChildren().add(b);

                musicButtonPane.add(gridPaneElement, j, i);

                buttonMap.put(z, b);
                z ++;
            }
        }
    }

    class GlobalKeyListener implements NativeKeyListener {

        public void nativeKeyPressed(NativeKeyEvent e) {
            try {
                int i = Integer.parseInt(NativeKeyEvent.getKeyText(e.getKeyCode()));
                currentTextChannel = jda.getTextChannelsByName(textChannelChoiceBox.getValue(), true).get(0);
                playerManager.loadAndPlay(currentTextChannel, MUSIC_PATH + buttonMap.get(i).getText());
            } catch (NumberFormatException ignored) {}
        }

        public void nativeKeyReleased(NativeKeyEvent ignored) { }

        public void nativeKeyTyped(NativeKeyEvent ignored) { }
    }

}




