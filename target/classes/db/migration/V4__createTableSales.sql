ALTER TABLE albums
ADD COLUMN price DECIMAL(13, 2) NOT NULL AFTER slug;

ALTER TABLE albums CHANGE COLUMN slug slug VARCHAR(250) NOT NULL;

CREATE TABLE sales (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  slug VARCHAR(250) NOT NULL,
  created_at DATETIME NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE sales_albums(
    id bigint(20) NOT NULL AUTO_INCREMENT,
    sales_id bigint(20) not null,
    album_name varchar(100),
    album_slug varchar(250),
    cashback int not null,
    album_price DECIMAL(13, 2),
    sale_price DECIMAL(13, 2),
    constraint pk_sales_albums_id primary key (id),
    FOREIGN KEY (sales_id) REFERENCES sales(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE cashback (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  genre VARCHAR(250) NOT NULL,
  day_of_week int NOT NULL,
  percent int NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO cashback (day_of_week, genre ,percent)
VALUES ('1', 'pop', '7');
INSERT INTO cashback (day_of_week, genre ,percent)
VALUES ('2', 'pop', '6');
INSERT INTO cashback (day_of_week, genre ,percent)
VALUES ('3', 'pop', '2');
INSERT INTO cashback (day_of_week, genre ,percent)
VALUES ('4', 'pop', '10');
INSERT INTO cashback (day_of_week, genre ,percent)
VALUES ('5', 'pop', '15');
INSERT INTO cashback (day_of_week, genre ,percent)
VALUES ('6', 'pop', '20');
INSERT INTO cashback (day_of_week, genre ,percent)
VALUES ('7', 'pop', '25');

INSERT INTO cashback (day_of_week, genre ,percent)
VALUES ('1', 'mpb', '5');
INSERT INTO cashback (day_of_week, genre ,percent)
VALUES ('2', 'mpb', '10');
INSERT INTO cashback (day_of_week, genre ,percent)
VALUES ('3', 'mpb', '15');
INSERT INTO cashback (day_of_week, genre ,percent)
VALUES ('4', 'mpb', '20');
INSERT INTO cashback (day_of_week, genre ,percent)
VALUES ('5', 'mpb', '25');
INSERT INTO cashback (day_of_week, genre ,percent)
VALUES ('6', 'mpb', '30');
INSERT INTO cashback (day_of_week, genre ,percent)
VALUES ('7', 'mpb', '30');

INSERT INTO cashback (day_of_week, genre ,percent)
VALUES ('1', 'classical', '3');
INSERT INTO cashback (day_of_week, genre ,percent)
VALUES ('2', 'classical', '5');
INSERT INTO cashback (day_of_week, genre ,percent)
VALUES ('3', 'classical', '8');
INSERT INTO cashback (day_of_week, genre ,percent)
VALUES ('4', 'classical', '13');
INSERT INTO cashback (day_of_week, genre ,percent)
VALUES ('5', 'classical', '18');
INSERT INTO cashback (day_of_week, genre ,percent)
VALUES ('6', 'classical', '25');
INSERT INTO cashback (day_of_week, genre ,percent)
VALUES ('7', 'classical', '35');

INSERT INTO cashback (day_of_week, genre ,percent)
VALUES ('1', 'rock', '10');
INSERT INTO cashback (day_of_week, genre ,percent)
VALUES ('2', 'rock', '15');
INSERT INTO cashback (day_of_week, genre ,percent)
VALUES ('3', 'rock', '15');
INSERT INTO cashback (day_of_week, genre ,percent)
VALUES ('4', 'rock', '15');
INSERT INTO cashback (day_of_week, genre ,percent)
VALUES ('5', 'rock', '20');
INSERT INTO cashback (day_of_week, genre ,percent)
VALUES ('6', 'rock', '40');
INSERT INTO cashback (day_of_week, genre ,percent)
VALUES ('7', 'rock', '40');