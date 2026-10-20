Отчет по лаботаротоной работе №2. Конфигурирование приложений Spring.

Цель работы: 
изучение и практическое освоение механизмов автоматического внедрения зависимостей через аннотации Spring Framework.

Выполнение работы:
1) Обновлён файл build.gradle. Нам понадобятся драйвер H2, модуль Spring JDBC и библиотека логирования Logback.
2) Создан файл настройки логирования в консоль logback.xml.
3) Создан файл schema.sql - SQL-скрипт создания таблиц.
4) Создан файл category.csv - новый файл с категориями.
5) Создан класс Category.java, определяющий модели данных.
6) Создан Product.java для удобства работы с БД.
7) Созданы CategoryProvider.java работающий как интерфейс и ConcreteProductProvider.java.
8) Созданы ProductProvider.java (Интерфейс) и ConcreteProductProvider.java - обновим старый класс.
9) Создадим DatabaseConfig.java, файл конфигурации БД.
10) Обновим интерфейс MessageRenderer.java.
11) Создадим DataBaseRenderer.java, он будет использоваться как рендер БД по умолчанию.
12) Создадим файл CategoryRequest.java с запросами к БД.
13) Обновим класс Main.java.
14) Создадим диаграмму Mermaid

Выводы: в ходе выполнения лабораторной работы была успешно модернизирована архитектура разрабатываемого Java-приложения за счет перехода от хранения данных в плоских CSV-файлах к использованию встроенной реляционной СУБД H2, функционирующей в оперативной памяти (In-Memory).

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