package ru.vokazak;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FxmlView("main-stage.fxml")
public class MyController {

    private final WeatherService weatherService;

    @Autowired
    public MyController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @FXML
    private Label weatherLabel;

    public void loadWeatherForecast(ActionEvent event) {
        this.weatherLabel.setText(weatherService.getWeatherForecast());
    }
}