package com.elevator.elevatorApp.Services;

import com.elevator.elevatorApp.Domain.Elevator;
import com.elevator.elevatorApp.Domain.ElevatorRequest;
import com.elevator.elevatorApp.Domain.ElevatorState;
import com.elevator.elevatorApp.Domain.Message.CurrentFloorMessage;
import com.elevator.elevatorApp.Domain.Message.ElevatorStateMessage;
import com.elevator.elevatorApp.Domain.Message.Message;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import static java.lang.Math.abs;

@Service
public class BuildingService implements PropertyChangeListener {
    private final Elevator A = new Elevator(0,"A");
    private final Elevator B = new Elevator(6,"B");
    private final HashMap<String, SseEmitter> emitters = new HashMap<>();

    public BuildingService() {
        A.addPropertyChangeListener(this);
        B.addPropertyChangeListener(this);
        emitters.put("A",new SseEmitter(-1L));
        emitters.put("B",new SseEmitter(-1L));
        A.start();
        B.start();
    }

    //one elevator is already headed to that destination -> do nothing
    //else -> add request for elevator and notify it in case the thread is waiting for requests
    public ElevatorRequest addInternalElevatorRequest(ElevatorRequest request, String elevatorLabel){
        if(Objects.equals(B.getDestination(), request.getDestination()) ||
                Objects.equals(A.getDestination(), request.getDestination()))
            return request;

        if(Objects.equals(elevatorLabel, "A"))
            synchronized (A){
                A.addRequest(request);
                A.notify();
            }
        else
        {
            synchronized (B){
                B.addRequest(request);
                B.notify();
            }
        }

        return request;
    }

    //both are waiting -> compare current floor
    //one is waiting -> send the one that is not waiting
    //both are moving -> send the one that has the destination closer to this destination
    public ElevatorRequest addExternalRequest(ElevatorRequest request){

        if(A.getState()== Thread.State.WAITING && B.getState()==Thread.State.WAITING)
            return processBothElevatorsAreAvailable(request);

        else if(A.getState() == Thread.State.WAITING)
            return addInternalElevatorRequest(request, "A");

        else if(B.getState() == Thread.State.WAITING)
            return addInternalElevatorRequest(request, "B");

        else
            return processBothElevatorsAreBusy(request);

    }

    public SseEmitter getEmitter(String key){
        return emitters.get(key);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        var propertyname = evt.getPropertyName().split(" ")[0];

        var elevatorLabel = evt.getPropertyName().split(" ")[1];

        switch (propertyname){
            case "currentFloor" -> sendCurrentFloorMessage(evt, elevatorLabel);
            case "state" -> sendStateMessage(evt, elevatorLabel);
        }
    }

    private void sendStateMessage(PropertyChangeEvent evt, String elevatorLabel) {
        Message message = new ElevatorStateMessage(elevatorLabel,(ElevatorState) evt.getNewValue());
        try {
            emitters.get(elevatorLabel).send(message.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void sendCurrentFloorMessage(PropertyChangeEvent evt, String elevatorLabel) {
        Message message = new CurrentFloorMessage(elevatorLabel, (Integer) evt.getNewValue());
        try {
            emitters.get(elevatorLabel).send(message.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private ElevatorRequest processBothElevatorsAreBusy(ElevatorRequest request) {
        var distanceFromADestination = abs(request.getDestination()-A.getDestination());
        var distanceFromBDestination = abs(request.getDestination()-B.getDestination());

        if(distanceFromADestination<distanceFromBDestination)
            return addInternalElevatorRequest(request,"A");
        else if(distanceFromADestination>distanceFromBDestination)
            return addInternalElevatorRequest(request,"B");
        else
            return (A.getDestination()<B.getDestination()?
                    addInternalElevatorRequest(request,"A") :
                    addInternalElevatorRequest(request,"B")
                    );
    }

    private ElevatorRequest processBothElevatorsAreAvailable(ElevatorRequest request) {
        var distanceFromA = abs(request.getDestination()-A.getCurrentFloor());
        var distanceFromB = abs(request.getDestination()-B.getCurrentFloor());

        if(distanceFromA<distanceFromB)
            return addInternalElevatorRequest(request,"A");
        else if(distanceFromA>distanceFromB)
            return addInternalElevatorRequest(request,"B");
        else
            return (A.getCurrentFloor()<B.getCurrentFloor()?
                    addInternalElevatorRequest(request,"A") :
                    addInternalElevatorRequest(request,"B")
            );

    }
}

