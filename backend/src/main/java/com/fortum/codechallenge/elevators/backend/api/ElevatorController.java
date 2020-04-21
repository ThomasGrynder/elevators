package com.fortum.codechallenge.elevators.backend.api;

import java.util.List;

import org.springframework.http.codec.ServerSentEvent;

import reactor.core.publisher.Flux;

/**
 * Interface for the Elevator Controller.
 */
public interface ElevatorController {

    /**
     * Request an elevator to the specified floor.
     *
     * @param toFloor
     *            addressed floor as integer.
     * @return The Elevator that is going to the floor, if there is one to move.
     */
    Elevator requestElevator(int toFloor);

    /**
     * A snapshot list of all elevators in the system.
     *
     * @return A List with all {@link Elevator} objects.
     */
    List<Elevator> getElevators();

    /**
     * Telling the controller that the given elevator is free for new operations.
     *
     * @param elevator
     *            the elevator that shall be released.
     */
    void releaseElevator(Elevator elevator);

    /**
     * Return an elevator object for provided identifier.
     *
     * @param elevatorId
     *            identifier of the elevator
     * @return An {@link Elevator} object.
     */
    Elevator getElevator(int elevatorId);

    /**
     * Stream of {@link Elevator} object events.
     *
     * @return An {@link Elevator} object wrapped into {@link ServerSentEvent}.
     */
    Flux<ServerSentEvent<Elevator>> eventStream();

}
