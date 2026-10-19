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
    }

    class DatabaseConfig {
        <<Configuration>>
        +dataSource() DataSource
        +jdbcTemplate(DataSource) JdbcTemplate
    }

    class Product {
        -int id
        -String name
        -String description
        -int categoryId
        -double price
        -int stockQuantity
    }

    class Category {
        -int id
        -String name
    }

    class ProductProvider {
        <<Interface>>
        +getProducts() List~Product~
    }

    class ConcreteProductProvider {
        <<Component>>
        +getProducts() List~Product~
    }

    class CategoryProvider {
        <<Interface>>
        +getCategories() List~Category~
    }

    class ConcreteCategoryProvider {
        <<Component>>
        +getCategories() List~Category~
    }

    class MessageRenderer {
        <<Interface>>
        +render() void
    }

    class DataBaseRenderer {
        <<Component>>
        <<Primary>>
        -JdbcTemplate jdbcTemplate
        +render() void
    }

    class CategoryRequest {
        <<Component>>
        -JdbcTemplate jdbcTemplate
        +execute() void
    }

    %% Связи
    ProductProvider <|.. ConcreteProductProvider
    CategoryProvider <|.. ConcreteCategoryProvider
    MessageRenderer <|.. DataBaseRenderer

    DataBaseRenderer --> ProductProvider : uses
    DataBaseRenderer --> CategoryProvider : uses
    DataBaseRenderer --> Category : inserts
    DataBaseRenderer --> Product : inserts

    CategoryRequest --> DatabaseConfig : uses JdbcTemplate
    
    Main ..> AppConfig : initializes
    Main ..> DatabaseConfig : initializes
    Main ..> MessageRenderer : calls render()
    Main ..> CategoryRequest : calls execute()
    ```