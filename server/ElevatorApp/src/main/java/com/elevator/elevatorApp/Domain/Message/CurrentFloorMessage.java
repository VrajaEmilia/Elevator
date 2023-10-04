package com.elevator.elevatorApp.Domain.Message;

public class CurrentFloorMessage implements Message{
    private final String elevatorLabel;
    private final int currentFloor;

    public CurrentFloorMessage(String elevatorLabel, int currentFloor) {
        this.elevatorLabel = elevatorLabel;
        this.currentFloor = currentFloor;
    }

    @Override
    public String getMessage() {
        return "{ \"elevatorLabel\": \"" + elevatorLabel + "\", \"currentFloor\":" + currentFloor + "}";
    }
}
