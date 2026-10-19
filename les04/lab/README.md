Отчет по лаботаротоной работе №2. Конфигурирование приложений Spring.

Цель работы: 
изучение и практическое освоение механизмов автоматического внедрения зависимостей через аннотации Spring Framework.

Выполнение работы:
1) Обновлён файл build.gradle для работы AOP и аннотации.
2) Создан файл настроек application.properties.
3) Обновлён AppConfig.java, чтобы программа автоматически искала классы с @Component.
4) Создан новый класс ResourceFileReader.java, он будет отвечать только за чтение файла.
5) Обновлён класс PetStoreMessageProvider.java, теперь он зависит от ResourceFileReader и формирует HTML-строку.
6) Создан ConsoleTableRenderer.java (переименованный старый OutMessageRenderer), оставим его для истории и как альтернативу.
7) Создан HTMLTableRenderer.java - новый класс, главный рендерер, помечаем его @Primary, чтобы Spring внедрял именно его, если запросят MessageRenderer.
8) Создан TimingAspect.java - аспект для замера времени.
9) Обновлён Main.java.
7) Создал диаграмму Mermaid

Выводы: В ходе лабораторной работы были успешно освоены продвинутые концепции экосистемы Spring Framework.
Реализован гибкий механизм слабой связанности компонентов за счет автоматического внедрения зависимостей (DI)
вместо ручного связывания объектов.

Диаграмма Mermaid:

```mermaid
classDiagram
    direction TB

    class Main {
        +main(String[] args)
    }

    class AppConfig {
        <<Configuration>>
        <<ComponentScan>>
        <<EnableAspectJAutoProxy>>
        <<PropertySource>>
    }

    class ResourceFileReader {
        <<Component>>
        -String fileName
        +init() void
        +parseCsv() List~String[]~
    }

    class MessageProvider {
        <<Interface>>
        +getMessage() String
    }

    class PetStoreMessageProvider {
        <<Component>>
        -ResourceFileReader fileReader
        +getMessage() String
    }

    class MessageRenderer {
        <<Interface>>
        +render() void
        +setMessageProvider(MessageProvider provider) void
        +getMessageProvider() MessageProvider
    }

    class ConsoleTableRenderer {
        <<Component>>
        +render() void
    }

    class HTMLTableRenderer {
        <<Component>>
        <<Primary>>
        +render() void
    }

    class TimingAspect {
        <<Aspect>>
        <<Component>>
        +measureParsingTime(ProceedingJoinPoint joinPoint) Object
    }

    %% Наследование (Реализация интерфейсов)
    MessageProvider <|.. PetStoreMessageProvider
    MessageRenderer <|.. ConsoleTableRenderer
    MessageRenderer <|.. HTMLTableRenderer

    %% Зависимости (Внедрение через конструктор/сеттер)
    PetStoreMessageProvider --> ResourceFileReader : uses
    HTMLTableRenderer --> MessageProvider : uses
    ConsoleTableRenderer --> MessageProvider : uses

    %% AOP (Аспект перехватывает метод)
    TimingAspect ..> ResourceFileReader : intercepts parseCsv()

    %% Связи с точкой входа
    Main ..> AppConfig : initializes context
    Main ..> MessageRenderer : gets and calls
    ```