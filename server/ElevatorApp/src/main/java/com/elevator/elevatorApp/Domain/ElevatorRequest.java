package com.elevator.elevatorApp.Domain;

public class ElevatorRequest{
    private Integer destination;

    public ElevatorRequest() {}

    public ElevatorRequest(Integer destination) {
        this.destination = destination;
    }

    public Integer getDestination() {
        return destination;
    }
}
