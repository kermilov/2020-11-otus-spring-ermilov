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

insert into Book_Comments (book_id, comment) select b.id, 'Book 1 Comment 2' from Books b where b.name = 'Book 1'
insert into Book_Comments (book_id, comment) select b.id, 'Book 1 Comment 1' from Books b where b.name = 'Book 1'
insert into Book_Comments (book_id, comment) select b.id, 'Book 2 Comment 2' from Books b where b.name = 'Book 2'
insert into Book_Comments (book_id, comment) select b.id, 'Book 2 Comment 1' from Books b where b.name = 'Book 2'
