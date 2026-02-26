# PetHealth

Пет-проект для обучения и демонстрации микросервисной архитектуры. Система управления здоровьем питомцев: записи к ветеринару, профили пользователей, уведомления.

---

## Технологический стек

| Категория | Технологии |
|-----------|------------|
| **Язык** | Java 21 |
| **Фреймворки** | Spring Boot 3.3.1, Spring Cloud 2023.0.3 |
| **Сборка** | Maven (мультимодульный) |
| **База данных** | PostgreSQL 15, Spring Data JPA |
| **Аутентификация** | Keycloak, OAuth2 / JWT |
| **Очереди** | RabbitMQ |
| **API-документация** | SpringDoc OpenAPI (Swagger) |
| **Шаблоны email** | Thymeleaf |

---

## Структура проекта (мультимодульность)

```
PetHealth/
├── gateway/                    # API Gateway (порт 8088)
├── clinic-service/             # Клиники, питомцы, записи (порт 8082)
├── users/
│   ├── user-service/           # Управление пользователями (порт 8081)
│   └── users-dto/              # DTO для user-service
├── notifications/
│   ├── notifications-service/  # Email-уведомления (порт 8083)
│   └── notifications-dto/      # DTO и RabbitMQ-контракты
└── security-starter/           # Общий OAuth2/JWT security-модуль
```

### Модули

| Модуль | Назначение |
|--------|------------|
| **gateway** | Spring Cloud Gateway — маршрутизация запросов, объединение Swagger |
| **clinic-service** | Клиники, питомцы, записи на приём (appointments) |
| **user-service** | Профиль пользователя, интеграция с Keycloak, управление аккаунтом |
| **notifications-service** | Event-driven уведомления по email через RabbitMQ |
| **security-starter** | Общая настройка OAuth2 Resource Server, JWT для всех сервисов |
| **users-dto**, **notifications-dto** | Общие DTO и модели сообщений между сервисами |

---

## Архитектурные особенности

- **Микросервисы** — отдельный сервис на каждый домен
- **Database per service** — у каждого сервиса своя БД PostgreSQL
- **Event-driven** — RabbitMQ для событий (создание/отмена/посещение записи, регистрация пользователя)
- **Strategy** — `AppointmentNotificationStrategy` для разных типов уведомлений
- **Общие DTO** — shared-модули для согласованных моделей и RabbitMQ-сообщений

---

## Порты и доступ

| Сервис | Порт | Описание |
|--------|------|----------|
| Gateway | 8088 | Точка входа в API |
| Keycloak | 8080 | Аутентификация |
| user-service | 8081 | Пользователи |
| clinic-service | 8082 | Клиники, питомцы, записи |
| notifications-service | 8083 | Уведомления |
| RabbitMQ Management | 15672 | UI управления очередями |

### API через Gateway

- `/api/v1/users/` — профиль, смена пароля, верификация email
- `/api/v1/veterinary/` — клиники, питомцы, записи
- `/api-docs/`, `/swagger-ui/` — документация OpenAPI

---

## Запуск

### Через Docker Compose

```bash
docker-compose up -d
```

Поднимаются: PostgreSQL (4 экземпляра), RabbitMQ, Keycloak, все сервисы.

### Разработка (docker-compose-dev)

Только Keycloak и PostgreSQL:

```bash
docker-compose -f docker-compose-dev.yml up -d
```

Сервисы запускаются локально через Maven/IDE.

---

## Сборка

```bash
mvn clean install
```

---

## Роли и доступ

- **DOCTOR** — управление клиникой
- **EMAIL_VERIFIED** — создание записей
- `@PreAuthorize("isAuthenticated()")` — доступ для аутентифицированных пользователей

---


