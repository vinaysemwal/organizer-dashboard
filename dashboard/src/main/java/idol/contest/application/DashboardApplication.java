package idol.contest.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * Application entry point
 */
@SpringBootApplication
@ComponentScans(value = { @ComponentScan("idol.contest") })
public class DashboardApplication {

    public static void main(final String[] args) {
        SpringApplication.run(DashboardApplication.class, args);
        System.out.println("Dashboard Running");
    }
}
