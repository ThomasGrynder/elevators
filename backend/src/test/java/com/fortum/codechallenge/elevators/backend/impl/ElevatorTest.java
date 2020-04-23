package com.fortum.codechallenge.elevators.backend.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import com.fortum.codechallenge.elevators.backend.api.Elevator;

class ElevatorTest {

    @Test
    void testGetCurrentFloor_whenMoved_thenMovedAndNotBusy() {
        // given
        double speedInFloorsPerSecond = 1.1;
        Elevator elevator = ElevatorImpl.of(1, speedInFloorsPerSecond, null);

        // when
        elevator.moveElevator(1);

        // then
        Awaitility.await().atMost(1, TimeUnit.SECONDS).until(() -> {
            return !elevator.isBusy() && elevator.getCurrentFloor() == 1;
        });
    }

    @Test
    void testIsBusy_whenInitialized_thenNotBusy() {
        // given
        Elevator elevator = ElevatorImpl.of(1, 1.0, null);

        // then
        assertThat(elevator.isBusy()).isEqualTo(false);
    }

    @Test
    void testIsBusy_whenMoveToTheSameFloor_thenNotBusy() {
        // given
        Elevator elevator = ElevatorImpl.of(1, 1.0, null);

        // when
        elevator.moveElevator(0);

        // then
        assertThat(elevator.isBusy()).isEqualTo(false);
    }

    @Test
    void testIsBusy_whenMoving_thenBusy() {
        // given
        double speedInFloorsPerSecond = 0.9;
        Elevator elevator = ElevatorImpl.of(1, speedInFloorsPerSecond, null);

        // when
        elevator.moveElevator(1);

        // then
        Awaitility.await().atMost(1, TimeUnit.SECONDS).until(elevator::isBusy, equalTo(true));
    }
}
