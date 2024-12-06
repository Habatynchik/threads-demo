CREATE TABLE counter_log
(
    id        serial,
    value     int,
    timestamp timestamp not null,

    primary key (id)
);

CREATE TABLE thread_request_log
(
    id        serial,
    producers bigint,
    consumers bigint,
    timestamp timestamp not null,

    primary key (id)
);