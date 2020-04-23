package com.fortum.codechallenge.elevators.backend.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fortum.codechallenge.elevators.backend.api.Elevator;
import com.fortum.codechallenge.elevators.backend.api.ElevatorController;

import reactor.core.publisher.Flux;

/**
 * Rest Resource.
 */
@RestController
@RequestMapping("/rest/v1")
public final class ElevatorControllerEndPoints {

    @Autowired
    private ElevatorController elevatorController;

    @GetMapping(value = "/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping(value = "/elevators/events")
    public Flux<ServerSentEvent<Elevator>> getElevatorsEvents() {
        return elevatorController.eventStream();
    }

    @PostMapping(value = "/elevators/{toFloor}")
    public Elevator requestElevator(@PathVariable int toFloor) {
        return elevatorController.requestElevator(toFloor);
    }

    @GetMapping(value = "/elevators")
    public List<Elevator> getElevators() {
        return elevatorController.getElevators();
    }

    @DeleteMapping(value = "/elevators/{elevatorId}")
    public void releaseElevator(@PathVariable int elevatorId) {
        elevatorController.releaseElevator(elevatorController.getElevator(elevatorId));
    }
}
