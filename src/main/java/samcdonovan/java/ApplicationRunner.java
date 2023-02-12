package samcdonovan.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationRunner {
    public static void main(String[] args) {

        SpringApplication.run(ApplicationRunner.class, args);

        /*
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Frog Bookstore API!");
        System.out.println("Do you want to get or set a person's info?");
        System.out.println("(1) Create");
        System.out.println("(2) Read");
        System.out.println("(3) Update");
        System.out.println("(4) Delete");
        System.out.println("(Type 'get' or 'set' now.)");
        String userInput = scanner.nextLine();

        switch(userInput) {
            case ("1"):
            case ("2"):
            case ("3"):
            case ("4"):
            default:
                System.out.println(userInput + " is not a valid option.");

        }
        scanner.close();
      */
    }
}