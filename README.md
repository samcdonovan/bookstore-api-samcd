
<h1 align="center">Bookstore API (Frog Programming Test)</h1>


---

## Table of Contents
- [API Endpoints](#endpoints)
  - [CREATE](#create)
  - [READ](#read)
  - [UPDATE](#update)
  - [DELETE](#delete)
- [Tools and dependencies](#built_using)
- [Authors](#authors)

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
### DELETE<a name = "delete"></a>
DELETE
- `/books` - Deletes ALL books from the 'books' table
- `/books/{id}` 
  - `/books/6` - Deletes book with ID of 6

## Tools and dependencies<a name = "built_using"></a>
- [Maven](https://maven.apache.org/): Managing the project with dependencies, running tests and packaging into an executable file. 
- [Spring Boot](https://spring.io/): Ran a Spring application which handled mapping API requests to their respective paths.
- [H2](https://www.h2database.com/html/main.html): In-memory SQL database for storing books in the 'books' table.
- [JUnit 5](https://junit.org/junit5/): Unit testing for the API endpoints.
-	[Spring Test](https://docs.spring.io/spring-boot/docs/2.1.5.RELEASE/reference/html/boot-features-testing.html): Ran a mock Spring application for testing the API endpoints; [MockMvc](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/MockMvc.html) specifically handled test requests to the API and assertions on the data retrieved. 
- [Jackson databind](https://github.com/FasterXML/jackson-databind): Converted Book objects into JSON format for testing API paths.
-	[launch4j](https://launch4j.sourceforge.net/): Packaging the project into a .exe file (may remove in favour of .jar packaging)

## Author <a name = "authors"></a>
- [@samcdonovan](https://github.com/samcdonovan)
