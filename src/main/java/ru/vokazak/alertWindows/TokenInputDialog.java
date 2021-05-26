package ru.vokazak.alertWindows;

import javafx.scene.control.TextInputDialog;

public class TokenInputDialog extends TextInputDialog {

    public TokenInputDialog(String oldToken) {
        super(oldToken);
        setTitle("Token error");
        setHeaderText("Current discord bot token is incorrect");
        setContentText("Enter valid token:");
    }

}
