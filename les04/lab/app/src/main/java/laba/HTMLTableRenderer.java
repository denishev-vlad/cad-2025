package laba;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary // Говорим Spring использовать этот бин по умолчанию
public class HTMLTableRenderer implements MessageRenderer {

    private MessageProvider messageProvider;

    @Autowired
    @Override
    public void setMessageProvider(MessageProvider provider) {
        this.messageProvider = provider;
    }

    @Override
    public void render() {
        if (messageProvider == null) {
            throw new RuntimeException("Провайдер не установлен!");
        }
        String htmlContent = messageProvider.getMessage();
        try {
            Files.writeString(Paths.get("products.html"), htmlContent, StandardCharsets.UTF_8);
            System.out.println("[INFO] HTML-таблица успешно сохранена в файл: products.html");
        } catch (IOException e) {
            System.err.println("Ошибка записи HTML: " + e.getMessage());
        }
    }

    @Override
    public MessageProvider getMessageProvider() {
        return this.messageProvider;
    }
}