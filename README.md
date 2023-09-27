<h1 align="center">📓 Note [Telegram bot]</h1>

## About

Telegram-bot - for storing user notes, with the ability to share your notes with other users and create reminders.

### Goals

* ✅ Add basic functionality with storing user notes in a **List**;
* ✅ Integrate a database and start using it instead of a **List**;
* ✅ Add text and hints for existing commands;
* ✅ Add the ability to automatically generate a table structure in the database for easy deployment of the project;
* ✅ Add the ability to work with the bot using the **(reply/ inline) keyboard**;
* ✅ Add the ability to share your notes with other users;
* ❌ Add the ability to create notifications;
* ✅ Make a Dockerfile to run the project.

### Technologies

* Language: **Java**
* Framework: **Spring Framework**
* Database: **PostgreSQL**
* Technologies: **Telegrambots, Lombok, Liquibase**

## Installing

### Clone the project

```shell
git clone https://github.com/l1ve4code/telegram-note-bot.git
```

### Set your values in postgres.yml or production.yml

_(For example: username, password and etc, or **use defaults**)_

```yaml
  postgres:
    container_name: <your-container-name>
    image: postgres
    environment:
      POSTGRES_USER: <your-user-name>
      POSTGRES_PASSWORD: <your-password>
      PGDATA: /data/postgres
    volumes:
      - postgres:/data/postgres
    ports:
      - "5432:5432"
    restart: unless-stopped
```

### Replace the gaps in the config with your values

_(Properties path: src/main/properties.d/telegram.properties)_

```properties
spring.datasource.url=jdbc:postgresql://<your-host>:5432/<your-database>
spring.datasource.username=<your-username>
spring.datasource.password=<your-password>
spring.datasource.driver-class-name=org.postgresql.Driver

spring.liquibase.change-log=classpath:postgres/changelog.xml

telegram.bot.name=<your-bot-name>
telegram.bot.token=<your-bot-token>
```

### Project startup _(using IDE)_

First, you need to run docker-compose

```shell
docker-compose -f postgres.yml up
```

Then you need to start the Java project using your IDE

### Project startup _(using Docker)_

Just run this command and project will be built and launched automatically

```shell
docker-compose -f production.yml up
```

Good luck ✨

## Author

* Telegram: **[@live4code](https://t.me/live4code)**
* Email: **steven.marelly@gmail.com**