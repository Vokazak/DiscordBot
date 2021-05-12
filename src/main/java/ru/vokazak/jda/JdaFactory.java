package ru.vokazak.jda;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.stereotype.Component;

@Component
public class JdaFactory {

    private String token = "NzczOTU1NjcwODEzODM1MzQ2.X6QwYw.YG3KxPDfsvJoBjQDwlRayt5aQQU";

    private JDA jda;
    public JDA getJda() {
        if (jda == null) {
            JDABuilder jdaBuilder = JDABuilder.createDefault(token);
            try {
                jda = jdaBuilder.addEventListeners(new MsgHandler()).build();
                jda.awaitReady();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        return jda;
    }

}
