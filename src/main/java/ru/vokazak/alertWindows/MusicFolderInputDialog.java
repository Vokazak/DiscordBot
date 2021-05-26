package ru.vokazak.alertWindows;

import javafx.scene.control.TextInputDialog;
import ru.vokazak.exceptions.InvalidDataException;

public class MusicFolderInputDialog extends TextInputDialog {

    public MusicFolderInputDialog(String oldFolder, InvalidDataException e) {
        super(oldFolder);
        setTitle(e.getMessage());
        setContentText("Enter valid folder or copy music to current folder and press ok:");
    }

}
