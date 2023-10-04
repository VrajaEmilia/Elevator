package com.elevator.elevatorApp.Domain;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

public class Elevator extends Thread {
    private Integer currentFloor;
    private ElevatorState state;
    private Integer destination;
    private final String label;
    private final ReentrantLock lock;
    private final Queue<ElevatorRequest> requests;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public Elevator(int startingFloor, String label){
        setCurrentFloor(startingFloor);
        this.label = label;
        this.destination = null;
        this.lock = new ReentrantLock();
        this.requests = new LinkedList<>();
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void setCurrentFloor(Integer value) {
        this.currentFloor = value;
    }


    public void addRequest(ElevatorRequest request){
        this.requests.add(request);
        System.out.println("Elevator " + this.label);
        System.out.println("added request for floor:" + request.getDestination());
    }

    public Integer getDestination(){
        return this.destination;
    }

    public Integer getCurrentFloor(){
        return this.currentFloor;
    }

    public void executeRequest() throws InterruptedException {
        this.lock.lock();

        this.destination = this.requests.remove().getDestination();
        var direction = this.getDirection(destination);

        setNewState(direction);

        while(!Objects.equals(this.currentFloor, destination)) {
            moveElevator(direction);
            Thread.sleep(1000);
        }

        setNewState(null);
        Thread.sleep(700);

        System.out.println("Elevator " + this.label + " has reached " + destination);
        this.lock.unlock();
    }

    public boolean hasRequests(){
        return !this.requests.isEmpty();
    }

    @Override
    public void run() {
        while (true) {
            try {
                synchronized (this) {
                    if (this.requests.isEmpty()) {
                        System.out.println(this.label + " is waiting...");
                        support.firePropertyChange("state " + this.label, this.state, ElevatorState.WAITING);
                        this.state = ElevatorState.WAITING;
                        wait();
                    }
                }
                while (this.hasRequests()) {
                    this.executeRequest();
                }
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private int getDirection(int destination){
        if(destination>this.currentFloor)
            return 1;

        return -1;
    }

    private void setNewState(Integer direction){
        if(direction==null) {
            support.firePropertyChange("state " + this.label, this.state, ElevatorState.ARRIVED);
            this.state = ElevatorState.ARRIVED;
        }
        else{
            var newState = (direction > 0) ? ElevatorState.UP : ElevatorState.DOWN;
            support.firePropertyChange("state " + this.label, this.state, newState);
            this.state = newState;
        }
    }

    private void moveElevator(Integer direction){
        System.out.println("Elevator " + this.label + " currently at floor:" + this.currentFloor);
        Integer newValue = this.currentFloor + direction;
        support.firePropertyChange("currentFloor " + this.label , this.currentFloor, newValue);
        setCurrentFloor(this.currentFloor + direction);
    }

}
