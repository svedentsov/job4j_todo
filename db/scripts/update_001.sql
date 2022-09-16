CREATE TABLE users (
   id       SERIAL PRIMARY KEY,
   name     VARCHAR UNIQUE,
   email    VARCHAR UNIQUE,
   password TEXT
);

CREATE TABLE if not exists tasks (
    id          SERIAL PRIMARY KEY,
    name        TEXT,
    description TEXT,
    created     TIMESTAMP,
    done        BOOLEAN,
    user_id     INTEGER NOT NULL REFERENCES users (id)
);

CREATE TABLE if not exists categories (
   id   SERIAL PRIMARY KEY,
   name VARCHAR
);

CREATE TABLE if not exists tasks_categories (
    id              serial primary key,
    task_id         int not null references tasks,
    categories_id   INT NOT NULL REFERENCES categories
);

INSERT INTO categories (name) VALUES ('Дом'), ('Работа'), ('Хобби'), ('Разное');