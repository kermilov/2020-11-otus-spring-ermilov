insert into Authors (name) select 'Author 1'
insert into Authors (name) select 'Author 2'

insert into Genres (name) select 'Genre 1'
insert into Genres (name) select 'Genre 2'

insert into Books (name, author_id, genre_id) select 'Book 1', a.id, g.id from Authors a, Genres g where a.name = 'Author 1' and g.name = 'Genre 1'
insert into Books (name, author_id, genre_id) select 'Book 2', a.id, g.id from Authors a, Genres g where a.name = 'Author 2' and g.name = 'Genre 2'
insert into Books (name, author_id, genre_id) select 'Book 3', a.id, g.id from Authors a, Genres g where a.name = 'Author 1' and g.name = 'Genre 2'
insert into Books (name, author_id, genre_id) select 'Book 4', a.id, g.id from Authors a, Genres g where a.name = 'Author 2' and g.name = 'Genre 1'
