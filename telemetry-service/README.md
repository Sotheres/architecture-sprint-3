# Telemetry management service

## Описание

Проект "Telemetry management service" представляет собой микросервис для управления телеметрией.
Он принимает массив данных телеметрии и сохраняет его в БД.

## Структура проекта

Проект организован в соответствии со стандартной структурой Maven/Gradle проекта:

- **Основной класс приложения** аннотирован `@SpringBootApplication`.
- **Пакеты**:
  - `controller` - контроллеры для обработки HTTP-запросов.
  - `service` - сервисы для бизнес-логики.
  - `repository` - репозитории для доступа к базе данных.
  - `entity` - сущности JPA.
  - `dto` - объекты передачи данных (DTO).

## Зависимости

Проект использует следующие зависимости:

- Java 17
- Maven >=3.8.1 && <4.0.0
- `spring-boot-starter-web` - для создания веб-приложений.
- `spring-boot-starter-data-jpa` - для работы с JPA и базой данных.
- `postgresql` - драйвер для работы с PostgreSQL.
- `lombok` - для упрощения написания кода.

## Запуск

``` shell
mvn spring-boot:run
```

## Конфигурация

### Подключение к базе данных

Конфигурация подключения к PostgreSQL находится в файле `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://home-postgresql:5432/smart_home
    username: user
    password: password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

## Логирование

В проекте используется SLF4J для логирования.
