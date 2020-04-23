package com.fortum.codechallenge.elevators.backend.impl;

import java.time.Duration;
import java.util.Objects;

import org.springframework.http.codec.ServerSentEvent;

import com.fortum.codechallenge.elevators.backend.api.Elevator;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

public class ElevatorImpl implements Elevator {

    private final int id;
    private final double speedInFloorsPerSecond;
    private int addressedFloor;
    private int currentFloor;

    private FluxSink<ServerSentEvent<Elevator>> sink;

    private ElevatorImpl(int elevatorId, double speedInFloorsPerSecond, FluxSink<ServerSentEvent<Elevator>> sink) {
        this.id = elevatorId;
        this.speedInFloorsPerSecond = speedInFloorsPerSecond;
        this.sink = sink;
    }

    static Elevator of(int elevatorId, double speedInFloorsPerSecond, FluxSink<ServerSentEvent<Elevator>> sink) {
        return new ElevatorImpl(elevatorId, speedInFloorsPerSecond, sink);
    }

    @Override
    public Direction getDirection() {
        if (addressedFloor == currentFloor) {
            return Direction.NONE;
        } else {
            return addressedFloor > currentFloor ? Direction.UP : Direction.DOWN;
        }
    }

    @Override
    public int getAddressedFloor() {
        return addressedFloor;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void moveElevator(int toFloor) {
        this.addressedFloor = toFloor;
        Flux.interval(durationBetweenFloors())
            .takeWhile(e -> isBusy())
            .doFinally(e -> release())
            .subscribe(e -> {
                if (getDirection().equals(Direction.UP)) {
                    currentFloor++;
                } else {
                    currentFloor--;
                }
                sendElevatorMovedEvent(ElevatorEvent.ELEVATOR_MOVED);
            });
    }

    private Duration durationBetweenFloors() {
        return Duration.ofMillis(Math.round(1_000 / speedInFloorsPerSecond));
    }

    private void sendElevatorMovedEvent(ElevatorEvent eventType) {
        if (sink != null) {
            sink.next(ServerSentEvent.builder((Elevator) this).event(eventType.toString()).build());
        }
    }

    @Override
    public boolean isBusy() {
        return addressedFloor != currentFloor;
    }

    @Override
    public int getCurrentFloor() {
        return currentFloor;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof ElevatorImpl) {
            ElevatorImpl that = (ElevatorImpl) object;
            return this.id == that.id;
        }
        return false;
    }

    @Override
    public void release() {
        if (isBusy()) {
            addressedFloor = currentFloor;
            sendElevatorMovedEvent(ElevatorEvent.ELEVATOR_RELEASED);
        }
    }

}
