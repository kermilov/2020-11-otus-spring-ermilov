DROP TABLE IF EXISTS BOOK_COMMENTS;
DROP TABLE IF EXISTS BOOK_GENRES;
DROP TABLE IF EXISTS BOOKS;
DROP TABLE IF EXISTS AUTHORS;
DROP TABLE IF EXISTS GENRES;
CREATE TABLE AUTHORS(ID BIGINT IDENTITY PRIMARY KEY, NAME VARCHAR(255));
ALTER TABLE AUTHORS ADD CONSTRAINT UK_AUTHOR_NAME UNIQUE (NAME);
CREATE TABLE GENRES(ID BIGINT IDENTITY PRIMARY KEY, NAME VARCHAR(255));
ALTER TABLE GENRES ADD CONSTRAINT UK_GENRE_NAME UNIQUE (NAME);
CREATE TABLE BOOKS(ID BIGINT IDENTITY PRIMARY KEY, NAME VARCHAR(255), AUTHOR_ID BIGINT);
ALTER TABLE BOOKS ADD CONSTRAINT UK_BOOK_NAME UNIQUE (NAME);
ALTER TABLE BOOKS ADD CONSTRAINT FK_AUTHOR_ID FOREIGN KEY (AUTHOR_ID) REFERENCES AUTHORS;
CREATE TABLE BOOK_GENRES (BOOK_ID BIGINT,GENRE_ID BIGINT);
ALTER TABLE BOOK_GENRES ADD CONSTRAINT FK_BOOK_GENRES_BOOK_ID FOREIGN KEY (BOOK_ID) REFERENCES BOOKS;
ALTER TABLE BOOK_GENRES ADD CONSTRAINT FK_BOOK_GENRES_GENRE_ID FOREIGN KEY (GENRE_ID) REFERENCES GENRES;
CREATE TABLE BOOK_COMMENTS(ID BIGINT IDENTITY PRIMARY KEY, BOOK_ID BIGINT, COMMENT VARCHAR(255));
ALTER TABLE BOOK_COMMENTS ADD CONSTRAINT FK_BOOK_COMMENTS_BOOK_ID FOREIGN KEY (BOOK_ID) REFERENCES BOOKS;
