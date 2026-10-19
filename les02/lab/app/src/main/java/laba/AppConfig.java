package laba;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MessageProvider provider() {
        return new PetStoreMessageProvider(); // Используем новый класс
    }

    @Bean
    public MessageRenderer renderer() {
        MessageRenderer renderer = new OutMessageRenderer();
        renderer.setMessageProvider(provider());
        return renderer;
    }
}