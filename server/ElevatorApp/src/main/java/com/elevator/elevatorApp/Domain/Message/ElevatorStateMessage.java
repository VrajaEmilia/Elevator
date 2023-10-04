package com.elevator.elevatorApp.Domain.Message;

import com.elevator.elevatorApp.Domain.ElevatorState;

public class ElevatorStateMessage implements Message{
    private final String elevatorLabel;
    private final ElevatorState state;

    public ElevatorStateMessage(String elevatorLabel, ElevatorState state) {
        this.elevatorLabel = elevatorLabel;
        this.state = state;
    }

    @Override
    public String getMessage() {
        return "{ \"elevatorLabel\": \"" + elevatorLabel + "\", \"state\":\"" + state + "\"}";
    }
}
