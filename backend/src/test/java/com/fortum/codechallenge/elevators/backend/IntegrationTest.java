package com.fortum.codechallenge.elevators.backend;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fortum.codechallenge.elevators.backend.api.Elevator;
import com.fortum.codechallenge.elevators.backend.resources.ElevatorControllerEndPoints;

/**
 * Boiler plate test class to get up and running with a test faster.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class IntegrationTest {

    @Autowired
    private ElevatorControllerEndPoints endpoints;

    @Value("${com.fortum.codechallenge.elevatorSpeedInFloorsPerSecond}")
    private double speedInFloorsPerSecond;

    @Test
    public void simulateAnElevatorShaft() {
        int firstRequestFloor = 4;
        int secondRequestFloor = 1;
        Elevator firstRequestElevator = endpoints.requestElevator(firstRequestFloor);
        Elevator secondRequestElevator = endpoints.requestElevator(secondRequestFloor);

        Awaitility.await()
                  .atMost(Math.round(1_000 / speedInFloorsPerSecond) * firstRequestFloor, TimeUnit.MILLISECONDS)
                  .until(() -> endpoints.getElevators()
                                        .stream()
                                        .filter(e -> e.getId() == firstRequestElevator.getId())
                                        .findFirst()
                                        .get()
                                        .getCurrentFloor() == firstRequestFloor);
        Awaitility.await()
                  .atMost(Math.round(1_000 / speedInFloorsPerSecond) * secondRequestFloor, TimeUnit.MILLISECONDS)
                  .until(() -> endpoints.getElevators()
                                        .stream()
                                        .filter(e -> e.getId() == secondRequestElevator.getId())
                                        .findFirst()
                                        .get()
                                        .getCurrentFloor() == secondRequestFloor);

        int thirdRequestFloor = 2;
        Elevator thirdRequestElevator = endpoints.requestElevator(thirdRequestFloor);

        assertThat(thirdRequestElevator.getId()).isEqualTo(secondRequestElevator.getId());
    }
}
