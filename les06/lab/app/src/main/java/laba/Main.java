package laba;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class, DatabaseConfig.class)) {
            // 1. Сохраняем данные в БД (вызовется DataBaseRenderer)
            MessageRenderer renderer = ctx.getBean(MessageRenderer.class);
            renderer.render();

            // 2. Выполняем запрос и логируем результат
            CategoryRequest request = ctx.getBean(CategoryRequest.class);
            request.execute();
        }
    }
}