package laba;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("laba")
@EnableAspectJAutoProxy // Включаем поддержку AOP
@PropertySource("classpath:application.properties") // Подключаем наш файл настроек
public class AppConfig {
}