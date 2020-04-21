package com.fortum.codechallenge.elevators.backend.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;

import com.fortum.codechallenge.elevators.backend.api.Elevator;
import com.fortum.codechallenge.elevators.backend.api.ElevatorController;
import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;

import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Service
public class ElevatorControllerImpl implements ElevatorController {

    @Value("${com.fortum.codechallenge.numberOfElevators}")
    private int numberOfElevators;

    @Value("${com.fortum.codechallenge.elevatorSpeedInFloorsPerSecond}")
    private double speedInFloorsPerSecond;

    private final ConcurrentMap<Integer, Elevator> elevatorsMap = new ConcurrentHashMap<>();

    private EmitterProcessor<ServerSentEvent<Elevator>> processor = EmitterProcessor.create();
    private FluxSink<ServerSentEvent<Elevator>> sink = processor.sink();

    private EventBus eventBus;

    public ElevatorControllerImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @PostConstruct
    public void initElevators() {
        IntStream.range(0, numberOfElevators)
                 .mapToObj(i -> ElevatorImpl.of(i, speedInFloorsPerSecond, eventBus, sink))
                 .forEach(e -> elevatorsMap.put(e.getId(), e));
    }

    @Override
    public synchronized Elevator requestElevator(int toFloor) {
        Elevator elevator = elevatorsMap.values()
                                        .stream()
                                        .filter(e -> !e.isBusy())
                                        .sorted(Comparator.comparingInt(e -> Math.abs(toFloor - e.getCurrentFloor())))
                                        .findFirst()
                                        .orElseThrow();
        elevator.moveElevator(toFloor);
        return elevator;
    }

    @Override
    public List<Elevator> getElevators() {
        return ImmutableList.copyOf(elevatorsMap.values());
    }

    @Override
    public void releaseElevator(Elevator elevator) {
        elevator.release();
    }

    @Override
    public Elevator getElevator(int elevatorId) {
        return Optional.ofNullable(elevatorsMap.get(elevatorId)).orElseThrow();
    }

    @Override
    public Flux<ServerSentEvent<Elevator>> eventStream() {
        return processor;
    }
}
