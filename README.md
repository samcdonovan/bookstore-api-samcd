
<h1 align="center">Bookstore API (Frog Programming Test)</h1>


---

## Table of Contents
- [Setup](#setup)
- [API Endpoints](#endpoints)
  - [CREATE](#create)
  - [READ](#read)
  - [UPDATE](#update)
  - [DELETE](#delete)
- [Tools and dependencies](#built_using)
- [Authors](#authors)

## Setup<a name = "setup"></a>
### Install and run through command line
 ```
 git clone https://github.com/samcdonovan/Bookstore-API.git
 cd bookstore-api-samcd
 java -jar .\frogAPI-1.0.0.jar
 ```

## API Endpoints<a name = "endpoints"></a>
Below are the endpoints for the API, with some examples. If the example is a URL, '%20' represents a space in the string.
### CREATE<a name = "create"></a>
POST
- `/books` - Inserts a given book into the 'books' table
  - JSON example:
      ```JSON
      {
          "title": "Example POST Title", 
          "author": "Example POST Author", 
          "isbn": "00000000000", 
          "price": 10.65
      }
      ```
### READ<a name = "read"></a>
GET
- `/books` - Retrieves ALL books from the 'books' table
- `/books/{id}`
    - `/books/1` - Retrieves the book with ID 1
    - `/books/4` - Retrieves the book with ID 4
- `/books?title={title}` 
    - `/books?title=Return%20Of%20The%20King` - Retrieves all books with 'Return Of The King' in their title
    - `/books?title=1984` - Retrieves all books with '1984' in their title
- `/books?author={author}`
  - `/books?author=George` - Retrieves all books with author fields containing 'George'
  - `/books?author=Neil%20Gaiman` - Retrieves all books with author fields containing 'Neil Gaiman'
- `/books?isbn={isbn}`
  - `/books?isbn=9780261103569` - Retrieves the book with ISBN 9780261103569
  - `/books?isbn=9780261103569` - Retrieves all books that contain 978026110 in their ISBN
- These parameters can also be used in combination with each other e.g.:
  - `/books?title=the&author=george` - Retrieves all books with 'the' in the title and 'george' in the author
  - `/books?title=two&author=tolkien&isbn=9780261103580` - Retrieves all books with 'two' in the title, 'tolkien' in the author and '9780261103580' in the isbn
### UPDATE<a name = "update"></a>
PUT
- `/books{id}`
  - `/books/5` - Updates book with ID 5 with the new book object
    - JSON example:
    ```JSON
    {
      "title": "Example PUT title",
      "author": "Example PUT author",
      "isbn": "00000000000",
      "price": 2.5
    }
    ```

PATCH
- `/books/{id}`
  - `/books/2` - Updates book with ID 2 with new fields
    - JSON example:
    ```JSON
    {
    "title": "Example PATCH title"
    }
    ```
  - `/books/4` - Updates book with ID 4 with new fields
    - JSON example:
    ```JSON
    {
    "author": "Example PATCH author",
    "price": 8.99
    }
    ```
### DELETE<a name = "delete"></a>
DELETE
- `/books` - Deletes ALL books from the 'books' table
- `/books/{id}` 
  - `/books/6` - Deletes book with ID 6
  - `/books/1` - Deletes book with ID 1

## Tools and dependencies<a name = "built_using"></a>
- [Maven](https://maven.apache.org/): Managing the project with dependencies, running tests and packaging into an executable file. 
- [Spring Boot](https://spring.io/): Ran a Spring application which handled mapping API requests to their respective paths.
- [H2](https://www.h2database.com/html/main.html): In-memory SQL database for storing books in the 'books' table.
- [JUnit 5](https://junit.org/junit5/): Unit testing for the API endpoints.
-	[Spring Test](https://docs.spring.io/spring-boot/docs/2.1.5.RELEASE/reference/html/boot-features-testing.html): Ran a mock Spring application for testing the API endpoints; [MockMvc](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/MockMvc.html) specifically handled test requests to the API and assertions on the data retrieved. 
- [Jackson databind](https://github.com/FasterXML/jackson-databind): Converted Book objects into JSON format for testing API paths.

## Author <a name = "authors"></a>
- [@samcdonovan](https://github.com/samcdonovan)
