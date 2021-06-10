package ru.vokazak.exceptions;

public class SettingsException extends Exception {

    public SettingsException() {
        super();
    }

    public SettingsException(String message) {
        super(message);
    }

    public SettingsException(Exception e) {
        super(e);
    }

    public SettingsException(String message, Exception e) {
        super(message, e);
    }
}
