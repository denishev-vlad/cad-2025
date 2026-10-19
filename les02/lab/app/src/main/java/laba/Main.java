package laba;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        
        // Оборачиваем контекст в try-with-resources. 
        // Он закроется автоматически после вызова renderer.render()
        try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class)) {
            
            MessageRenderer renderer = ctx.getBean("renderer", MessageRenderer.class);
            renderer.render();
            
        } // Здесь контекст закроется автоматически
        
    }
}