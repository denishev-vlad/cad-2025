package laba;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class)) {
            // Благодаря @Primary, Spring подставит сюда HTMLTableRenderer
            MessageRenderer renderer = ctx.getBean(MessageRenderer.class);
            renderer.render();
        }
    }
}