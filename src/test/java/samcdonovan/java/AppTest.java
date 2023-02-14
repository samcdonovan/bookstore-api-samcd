package samcdonovan.java;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import samcdonovan.java.controller.BookController;
import samcdonovan.java.model.Book;

@SpringBootTest
@AutoConfigureMockMvc
public class AppTest {

    @Autowired
    private BookController bookController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Book controller loads and is not null")
    public void controllerLoads() throws Exception {
        assertThat(bookController).isNotNull();
    }

    @Test
    @DisplayName("POST path /books; inserts document into database correctly")
    public void postPathCorrectlyInserts() throws Exception {

    }

    @Test
    @DisplayName("GET path /books; retrieves all documents in database")
    public void getPathRetrievesAllDocuments() throws Exception {

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(6))); // database initially contains 6 documents
    }

    @Test
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
    @DisplayName("PUT path /books/{id}; updates document with given ID")
    public void putPathCorrectlyUpdates() throws Exception {

        
    }

    @Test
    @DisplayName("DELETE path /books/{id}; deletes document with given ID")
    public void deletePathCorrectlyDeletes() throws Exception {
        mockMvc.perform(delete("/books/4"))
                .andExpect(status().isOk())
                //.andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").doesNotExist());

        mockMvc.perform(get("/books/4"))
                .andExpect(status().isNotFound());

    }



}
