package samcdonovan.java;

import org.junit.jupiter.api.*;

import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import samcdonovan.java.controller.BookController;
import samcdonovan.java.model.Book;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppTest {

    @Autowired
    private BookController bookController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    @DisplayName("Book controller loads and is not null")
    public void controllerLoads() throws Exception {
        assertThat(bookController).isNotNull();
    }

    @Test
    @Order(3)
    @DisplayName("POST path /books; inserts document into database correctly")
    public void postPathCorrectlyInserts() throws Exception {
        Book testBook = new Book("TestTitle", "TestAuthor", "1111111111111", 9.6);
        ObjectMapper objMapper = new ObjectMapper();
        String bookJson = objMapper.writeValueAsString(testBook);

        mockMvc.perform(post("/books")
                        .content(String.valueOf(bookJson))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    @DisplayName("GET path /books; retrieves all documents in database")
    public void getPathRetrievesAllDocuments() throws Exception {

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(8))); // database initially contains 6 documents
    }

    @Test
    @Order(4)
    @DisplayName("GET path /books/{id}; retrieves document with correct ID")
    public void getPathRetrievesCorrectId() throws Exception {

        mockMvc.perform(get("/books/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].title", is("1984")))
                .andExpect(jsonPath("$[0].author", is("George Orwell")))
                .andExpect(jsonPath("$[0].isbn", is("9780261103569")))
                .andExpect(jsonPath("$[0].price", is(5.0)));
    }

    @Test
    @Order(4)
    @DisplayName("GET path /books?author={author}; retrieves documents with authors containing the given name")
    public void getPathRetrievesBooksWithGivenAuthor() throws Exception {

        mockMvc.perform(get("/books?author=J.R.R. Tolkien"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].title", is("The Fellowship Of The Ring")))
                .andExpect(jsonPath("$[1].title", is("The Two Towers")))
                .andExpect(jsonPath("$[2].title", is("Return Of The King")))
                .andExpect(jsonPath("$[*].author", everyItem(containsString("J.R.R. Tolkien"))));
    }

    @Test
    @Order(4)
    @DisplayName("GET path /books?title={title}; retrieves documents containing given title")
    public void getPathRetrievesBooksWithGivenTitle() throws Exception {

        mockMvc.perform(get("/books?title=The"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].title", containsString("The")))
                .andExpect(jsonPath("$[1].title", containsString("The")))
                .andExpect(jsonPath("$[2].title", containsString("The")))
                .andExpect(jsonPath("$[2].title", containsString("The")))
                .andExpect(jsonPath("$[2].title", containsString("The")));
    }

    @Test
    @Order(4)
    @DisplayName("GET path /books?isbn={isbn}; retrieves documents containing given ISBN")
    public void getPathRetrievesBooksWithGivenIsbn() throws Exception {

        mockMvc.perform(get("/books?isbn=9780261103570"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1))) // should only return one document
                .andExpect(jsonPath("$[0].isbn", is("9780261103570")));
    }

    @Test
    @Order(4)
    @DisplayName("GET path /books?price={price}; retrieves documents with given price")
    public void getPathRetrievesBooksWithGivenPrice() throws Exception {

        mockMvc.perform(get("/books?price=6.5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3))) // should only return one document
                .andExpect(jsonPath("$[0].price", is(6.5)))
                .andExpect(jsonPath("$[1].price", is(6.5)))
                .andExpect(jsonPath("$[2].price", is(6.5)));
    }

    @Test
    @Order(4)
    @DisplayName("GET path /books?title={title}&author={author}; retrieves documents containing given title and author")
    public void getRetrievesBookWithTitleAndAuthor() throws Exception {

        /* check that doing a combination search with a title of 'the' and an author
        of 'george' returns only documents containing those parameters */
        mockMvc.perform(get("/books?title=the&author=george"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", containsString("The")))
                .andExpect(jsonPath("$[0].author", containsString("George")))
                .andExpect(jsonPath("$[1].title", containsString("The")))
                .andExpect(jsonPath("$[1].author", containsString("George")));
    }

    @Test
    @Order(5)
    @DisplayName("PUT path /books/{id}; updates document with given ID")
    public void putPathCorrectlyUpdates() throws Exception {
        Book testBook = new Book("PutTestTitle", "PutTestAuthor", "000000000000", 12.34);

        ObjectMapper objMapper = new ObjectMapper();
        String bookJson = objMapper.writeValueAsString(testBook); // map book to json

        mockMvc.perform(put("/books/6")
                        .content(String.valueOf(bookJson))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        /* check that book with ID 6 has been changed to the test book */
        mockMvc.perform(get("/books/6")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(6)))
                .andExpect(jsonPath("$[0].title", is("PutTestTitle")))
                .andExpect(jsonPath("$[0].author", is("PutTestAuthor")))
                .andExpect(jsonPath("$[0].isbn", is("000000000000")))
                .andExpect(jsonPath("$[0].price", is(12.34)));
    }

    @Test
    @Order(6)
    @DisplayName("PATCH path /books/{id}; updates given fields of document with the given ID")
    public void patchUpdatesCorrectly() throws Exception {
        Book testBook = new Book("PatchTestTitle", null, null, 0.0);

        ObjectMapper objMapper = new ObjectMapper();
        objMapper.configure(ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        String bookJson = objMapper.writeValueAsString(testBook); // map book to json

        /* update title field of book */
        mockMvc.perform(patch("/books/3")
                        .content(String.valueOf(bookJson))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        /* check that the title field has been updated */
        mockMvc.perform(get("/books/3")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(3)))
                .andExpect(jsonPath("$[0].title", is("PatchTestTitle")))
                .andExpect(jsonPath("$[0].author", is("J.R.R. Tolkien")))
                .andExpect(jsonPath("$[0].isbn", is("9780261103580")))
                .andExpect(jsonPath("$[0].price", is(6.5)));

        /* update multiple fields */
        testBook.setAuthor("PatchTestAuthor");
        testBook.setIsbn("2222222222222");
        testBook.setTitle(null);
        bookJson = objMapper.writeValueAsString(testBook);

        mockMvc.perform(patch("/books/3")
                        .content(String.valueOf(bookJson))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        /* check that the books' fields reflect the new changes */
        mockMvc.perform(get("/books/3")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(3)))
                .andExpect(jsonPath("$[0].title", is("PatchTestTitle")))
                .andExpect(jsonPath("$[0].author", is("PatchTestAuthor")))
                .andExpect(jsonPath("$[0].isbn", is("2222222222222")))
                .andExpect(jsonPath("$[0].price", is(6.5)));
    }

    @Test
    @Order(6)
    @DisplayName("DELETE path /books/{id}; deletes document with given ID")
    public void deletePathCorrectlyDeletes() throws Exception {

        /* delete book with ID 4 */
        mockMvc.perform(delete("/books/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());


        /* check that book with ID 4 no longer exists */
        mockMvc.perform(get("/books/4"))
                .andExpect(status().isNotFound());

    }

    @Test
    @Order(7)
    @DisplayName("DELETE path /books; deletes all rows in 'books' table")
    public void deleteAllBooks() throws Exception {

        /* delete all books using delete path */
        mockMvc.perform(delete("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

        /* check that get all books returns NO_CONTENT */
        mockMvc.perform(get("/books"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
