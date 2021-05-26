package ru.vokazak.alertWindows;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ErrorAlertWindow extends Alert {
    public ErrorAlertWindow(String message) {
        super(AlertType.ERROR, message, ButtonType.OK);
    }
}
