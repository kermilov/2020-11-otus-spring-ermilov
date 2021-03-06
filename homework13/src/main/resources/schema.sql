DROP TABLE IF EXISTS BOOK_COMMENTS;
DROP TABLE IF EXISTS BOOK_GENRES;
DROP TABLE IF EXISTS BOOKS;
DROP TABLE IF EXISTS AUTHORS;
DROP TABLE IF EXISTS GENRES;
DROP TABLE IF EXISTS USERS;
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
CREATE TABLE USERS(ID BIGINT IDENTITY PRIMARY KEY, NAME VARCHAR(255), PASSWORD VARCHAR(255), ROLES VARCHAR(255));
CREATE TABLE BOOK_COMMENTS(ID BIGINT IDENTITY PRIMARY KEY, BOOK_ID BIGINT, USER_ID BIGINT, COMMENT VARCHAR(255));
ALTER TABLE BOOK_COMMENTS ADD CONSTRAINT FK_BOOK_COMMENTS_BOOK_ID FOREIGN KEY (BOOK_ID) REFERENCES BOOKS;
ALTER TABLE BOOK_COMMENTS ADD CONSTRAINT FK_BOOK_COMMENTS_USER_ID FOREIGN KEY (USER_ID) REFERENCES BOOKS;

CREATE TABLE IF NOT EXISTS acl_sid (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  principal tinyint(1) NOT NULL,
  sid varchar(100) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_1 (sid,principal)
);

CREATE TABLE IF NOT EXISTS acl_class (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  class varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_2 (class)
);

CREATE TABLE IF NOT EXISTS acl_entry (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  acl_object_identity bigint(20) NOT NULL,
  ace_order int(11) NOT NULL,
  sid bigint(20) NOT NULL,
  mask int(11) NOT NULL,
  granting tinyint(1) NOT NULL,
  audit_success tinyint(1) NOT NULL,
  audit_failure tinyint(1) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_4 (acl_object_identity,ace_order)
);

CREATE TABLE IF NOT EXISTS acl_object_identity (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  object_id_class bigint(20) NOT NULL,
  object_id_identity bigint(20) NOT NULL,
  parent_object bigint(20) DEFAULT NULL,
  owner_sid bigint(20) DEFAULT NULL,
  entries_inheriting tinyint(1) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_3 (object_id_class,object_id_identity)
);

ALTER TABLE acl_entry
ADD FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity(id);

ALTER TABLE acl_entry
ADD FOREIGN KEY (sid) REFERENCES acl_sid(id);

--
-- Constraints for table acl_object_identity
--
ALTER TABLE acl_object_identity
ADD FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id);

ALTER TABLE acl_object_identity
ADD FOREIGN KEY (object_id_class) REFERENCES acl_class (id);

ALTER TABLE acl_object_identity
ADD FOREIGN KEY (owner_sid) REFERENCES acl_sid (id);