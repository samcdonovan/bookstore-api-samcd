package samcdonovan.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ApplicationRunner {
    public static void main(String[] args) {

        SpringApplication.run(ApplicationRunner.class, args);

    }

    /**
     * Helper function to print a message once the Spring
     * application is running
     */
    @EventListener(ApplicationReadyEvent.class)
    public void appReadyMessage() {
        System.out.println("\n---------------------------------------");
        System.out.println("Application has started at:");
        System.out.println("localhost:3000/books\n");
    }
}