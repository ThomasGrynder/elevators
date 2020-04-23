package com.fortum.codechallenge.elevators.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Preconfigured Spring Application boot class.
 */
@SpringBootApplication
public class ElevatorApplication {

    /**
     * Start method that will be invoked when starting the Spring context.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(ElevatorApplication.class, args);
    }
}
