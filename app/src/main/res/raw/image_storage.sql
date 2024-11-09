CREATE TABLE IF NOT EXISTS image_storage (
    id integer primary key autoincrement,
    name varchar(200) not null,
    description varchar null,
    image blob null
);