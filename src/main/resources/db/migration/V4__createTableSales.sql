ALTER TABLE albums
ADD COLUMN price VARCHAR(250) NOT NULL AFTER slug;

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
    albums_id bigint(20) not null,
    cashback int not null,
    album_price DECIMAL(13, 2),
    sale_price DECIMAL(13, 2),
    constraint pk_sales_albums_id primary key (id),
    FOREIGN KEY (albums_id) REFERENCES albums(id),
    FOREIGN KEY (sales_id) REFERENCES sales(id)
);