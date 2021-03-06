package ru.vokazak.config;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.vokazak.exceptions.SettingsException;

import java.io.*;

@Component
@Data
public class Settings implements Serializable {

    private final String FILE_PATH = "settings.out";

    private String token;
    private String musicFolder;
    private String[][] keyBindArray = new String[3][3];
    private String musicVoiceChannel;
    private String musicTextChannel;

    Settings() {
        token = "";
        musicFolder = "";
        musicVoiceChannel = "";
        musicTextChannel = "";

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                keyBindArray[i][j] = "-";
            }
        }
    }

    public void saveSettings() throws SettingsException {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    new FileOutputStream(FILE_PATH));
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
        } catch (IOException e) {
            throw new SettingsException("Exception while saving settings", e);
        }
    }

    public void loadSettings() throws SettingsException {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(
                    new FileInputStream(FILE_PATH)
            );

            Settings settings = (Settings) objectInputStream.readObject();
            this.token = settings.getToken();
            this.musicFolder = settings.getMusicFolder();
            this.keyBindArray = settings.getKeyBindArray();
            this.musicTextChannel = settings.getMusicTextChannel();
            this.musicVoiceChannel = settings.getMusicVoiceChannel();

            objectInputStream.close();
        } catch (IOException fileWasNotFoundExc) {
            createNewSettingsFile();
        } catch (ClassNotFoundException e) {
            throw new SettingsException(e);
        }
    }

    private void createNewSettingsFile() throws SettingsException {
        File settingsFile = new File(FILE_PATH);
        try {
            boolean isCreated = settingsFile.createNewFile();
            if (!isCreated) {
                throw new SettingsException();
            }
        } catch (IOException ioException) {
            throw new SettingsException("Unable to load settings", ioException);
        }
    }

    public void setNewMusicButtonPaneState(String newValue, int i, int j) {
        keyBindArray[j][i] = newValue;
    }


}
