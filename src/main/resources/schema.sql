--if a 'books' table already exists, DROP it
DROP TABLE IF EXISTS books;

--CREATE books table with id, title, author, isbn and price fields
CREATE TABLE books (
  id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(250) NOT NULL,
  author VARCHAR(250) NOT NULL,
  isbn VARCHAR(250) NOT NULL,
  price DOUBLE NOT NULL
);