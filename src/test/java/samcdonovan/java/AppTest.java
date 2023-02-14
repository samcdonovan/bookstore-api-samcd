package samcdonovan.java;

import org.junit.jupiter.api.*;

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
                .andExpect(jsonPath("$", hasSize(6))); // database initially contains 6 documents
    }

    @Test
    @Order(4)
    @DisplayName("GET path /books/{id}; retrieves document with correct ID")
    public void getPathRetrievesCorrectId() throws Exception {

        mockMvc.perform(get("/books/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.title", is("1984")))
                .andExpect(jsonPath("$.author", is("George Orwell")))
                .andExpect(jsonPath("$.isbn", is("9780261103569")))
                .andExpect(jsonPath("$.price", is(5.0)));
    }

    @Test
    @Order(5)
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
    @Order(6)
    @DisplayName("PUT path /books/{id}; updates document with given ID")
    public void putPathCorrectlyUpdates() throws Exception {
        Book testBook = new Book("PutTestTitle", "PutTestAuthor", "000000000000", 12.34);

        ObjectMapper objMapper = new ObjectMapper();
        String bookJson = objMapper.writeValueAsString(testBook);

        mockMvc.perform(put("/books/6")
                        .content(String.valueOf(bookJson))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/books/6")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(6)))
                .andExpect(jsonPath("$.title", is("PutTestTitle")))
                .andExpect(jsonPath("$.author", is("PutTestAuthor")))
                .andExpect(jsonPath("$.isbn", is("000000000000")))
                .andExpect(jsonPath("$.price", is(12.34)));
    }

    @Test
    @Order(7)
    @DisplayName("DELETE path /books/{id}; deletes document with given ID")
    public void deletePathCorrectlyDeletes() throws Exception {
        mockMvc.perform(delete("/books/4"))
                .andExpect(status().isOk())
                //.andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").doesNotExist());

        mockMvc.perform(get("/books/4"))
                .andExpect(status().isNotFound());

    }

    @Test
    @Order(8)
    @DisplayName("DELETE path /books; deletes all rows in 'books' table")
    public void deleteAllBooks() throws Exception {
        mockMvc.perform(delete("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

        mockMvc.perform(get("/books"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
