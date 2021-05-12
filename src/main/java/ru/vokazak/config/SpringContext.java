package ru.vokazak.config;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import ru.vokazak.ApplicationStarter;

@Configuration
public class SpringContext {

    private static ApplicationContext context;
    public static ApplicationContext getContext() {
        if (context == null) {
            context = new SpringApplicationBuilder()
                    .sources(ApplicationStarter.class)
                    .run();
        }
        return context;
    }
}
