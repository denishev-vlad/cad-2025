package laba;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;

@Component
public class HTMLTableRenderer implements MessageRenderer {

    private MessageProvider messageProvider;


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

}