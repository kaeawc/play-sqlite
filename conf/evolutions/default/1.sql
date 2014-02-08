
# --- !Ups

CREATE TABLE widget (
  id        INTEGER      NOT NULL PRIMARY KEY AUTOINCREMENT,
  name      VARCHAR(255) NOT NULL,
  created   DATETIME     NOT NULL
);

# --- !Downs

DROP TABLE widget;