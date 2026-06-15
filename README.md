# teacher-backend

Spring Boot 3 REST API для сайта учителя информатики.

## Быстрый старт

```bash
# 1. Клонировать фронтенд рядом
git clone https://github.com/ТВОЙ_ЮЗЕР/teacher-frontend.git frontend

# 2. Запустить
docker compose up -d
```

Сайт будет доступен на http://localhost:80.

## Разработка без Docker

```bash
mvn spring-boot:run
```

API: http://localhost:8080/api/schedule

## Структура

```
.
├── docker-compose.yml   # nginx + backend
├── nginx.conf           # прокси статики и /api/
├── Dockerfile           # multi-stage сборка Java
├── pom.xml              # Maven, Spring Boot 3.2
├── src/                 # Java-исходники
└── frontend/            # клонируется из teacher-frontend
```
