insert into Authors (name) select 'Author 1'
insert into Authors (name) select 'Author 2'

insert into Genres (name) select 'Genre 1'
insert into Genres (name) select 'Genre 2'

insert into Books (name, author_id) select 'Book 1', a.id from Authors a where a.name = 'Author 1'
insert into Books (name, author_id) select 'Book 2', a.id from Authors a where a.name = 'Author 2'
insert into Books (name, author_id) select 'Book 3', a.id from Authors a where a.name = 'Author 1'
insert into Books (name, author_id) select 'Book 4', a.id from Authors a where a.name = 'Author 2'

insert into Book_Genres (book_id, genre_id) select b.id, g.id from Books b, Genres g where b.name = 'Book 1' and g.name = 'Genre 1'
insert into Book_Genres (book_id, genre_id) select b.id, g.id from Books b, Genres g where b.name = 'Book 2' and g.name = 'Genre 2'
insert into Book_Genres (book_id, genre_id) select b.id, g.id from Books b, Genres g where b.name = 'Book 3' and g.name = 'Genre 2'
insert into Book_Genres (book_id, genre_id) select b.id, g.id from Books b, Genres g where b.name = 'Book 4' and g.name = 'Genre 1'

insert into Users (name, password, roles) select 'Kermilov', '{bcrypt}$2a$10$qYsehZsH1WTF0AgD6XIc9eWdAn0nLiecpFihfeF1g9DAa/eI5wJoy', 'USER'
insert into Users (name, password, roles) select 'OtusTeacher', '{bcrypt}$2a$10$s/S4UgP9rGdZV6HZi5TZsux7xZ//m12/farZ7IR6DtEY07L38F3Va', 'USER'

insert into Book_Comments (book_id, user_id, comment) select b.id, u.id, 'Book 1 Comment 1' from Books b, Users u where b.name = 'Book 1' and u.name = 'OtusTeacher'
insert into Book_Comments (book_id, user_id, comment) select b.id, u.id, 'Book 1 Comment 2' from Books b, Users u where b.name = 'Book 1' and u.name = 'Kermilov'

insert into acl_sid (principal, sid) select 1, u.name from Users u
insert into acl_sid (principal, sid) select distinct 0, u.roles from Users u
insert into acl_class (class) values ('ru.otus.spring.kermilov.books.domain.BookComment')
insert into acl_class (class) values ('ru.otus.spring.kermilov.books.dto.BookCommentDto')
insert into acl_object_identity (object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) select cl.id, bc.id, null, sid.id, 0 from acl_class cl, Book_Comments bc, acl_sid sid where sid.sid = 'USER'
insert into acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) select aoi.id, 0, sid.id, 16, 1, 0, 0 from acl_object_identity aoi, Book_Comments bc, Users u, acl_sid sid where bc.id = aoi.object_id_identity and u.id = bc.user_id and sid.sid = u.name
insert into acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) select aoi.id, 1, sid.id, 16, 0, 0, 0 from acl_object_identity aoi, Book_Comments bc, Users u, acl_sid sid where bc.id = aoi.object_id_identity and u.id <> bc.user_id and sid.sid = u.name