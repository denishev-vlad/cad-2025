package laba;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimingAspect {

    // Перехватываем выполнение метода parseCsv в классе ResourceFileReader
    @Around("execution(* laba.ResourceFileReader.parseCsv(..))")
    public Object measureParsingTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime();
        
        Object result = joinPoint.proceed(); // Выполняем сам метод
        
        long endTime = System.nanoTime();
        long durationMs = (endTime - startTime) / 1_000_000;
        
        System.out.println("[AOP] Парсинг CSV файла занял: " + durationMs + " мс.");
        return result;
    }
}
