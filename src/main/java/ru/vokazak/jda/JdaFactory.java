package ru.vokazak.jda;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.stereotype.Component;
import ru.vokazak.alertWindows.TokenInputDialog;
import ru.vokazak.config.Settings;
import ru.vokazak.exceptions.SettingsException;

import javax.security.auth.login.LoginException;
import java.util.Optional;

@Component
public class JdaFactory {

    private final Settings settings;
    public JdaFactory(Settings settings) {
        this.settings = settings;
        try {
            this.settings.loadSettings();
        } catch (SettingsException e) {
            System.exit(-1);
        }
    }

    private JDA jda;
    public JDA getJda() {
        if (jda == null) {
            jda = initJda(settings.getToken());
        }
        return jda;
    }

    public JDA initJda(String token) {
        try {
            JDA jda = JDABuilder
                    .createDefault(settings.getToken())
                    .addEventListeners(new MsgHandler())
                    .build();

            jda.awaitReady();
            return jda;
        } catch (LoginException e) {
            Optional<String> result = new TokenInputDialog(token).showAndWait();

            if (result.isPresent()){
                String tok = result.get();
                settings.setToken(tok);

                try {
                    settings.saveSettings();
                } catch (SettingsException settingsException) {
                    System.exit(-1);
                }

                return initJda(token);
            } else {
                System.exit(-1);
            }
        } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
        }
        return null;
    }

}
