create table if not exists users
(
    id         uuid primary key,
    login      varchar,
    password   varchar,
    first_name varchar,
    last_name  varchar
    );

create table if not exists points
(
    id             uuid primary key,
    x              float,
    y              float,
    r              float,
    is_hit         boolean,
    time_point     bigint,
    execution_time bigint,
    user_id        uuid
    );

alter table points
    add constraint fk_users
        foreign key (user_id)
            references users (id);

comment on table users is 'Пользователи';
comment on table points is 'Результаты выстрелов';