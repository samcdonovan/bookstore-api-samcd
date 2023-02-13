package samcdonovan.java;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import samcdonovan.java.controller.BookController;

@SpringBootTest
public class AppTest {

    @Autowired
    private BookController bookController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(bookController).isNotNull();
    }
}
