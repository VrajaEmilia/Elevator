import React, { useState, useEffect } from "react";
import "../styles/MainPage.css";
import Building from "../components/Building";
import ElevatorMenu from "../components/ElevatorMenu";
import { elevatorStates } from "../config";
import { setElevatorInformation } from "../api/elevatorInformation";
function MainPage() {
  const [currentFloorA, setCurrentFloorA] = useState(0);
  const [currentFloorB, setCurrentFloorB] = useState(6);
  const [elevatorAState, setElevatorAState] = useState(elevatorStates.WAITING);
  const [elevatorBState, setElevatorBState] = useState(elevatorStates.WAITING);

  useEffect(() => {
    setElevatorInformation("A", setCurrentFloorA, setElevatorAState);
    setElevatorInformation("B", setCurrentFloorB, setElevatorBState);
  }, []);

  return (
    <div className="main-page-layout">
      <div className="building-section">
        <Building
          currentFloorA={currentFloorA}
          currentFloorB={currentFloorB}
          elevatorAState={elevatorAState}
          elevatorBState={elevatorBState}
        />
      </div>
      <div className="elevator-menus-section">
        <p>Menus from inside the elevators:</p>
        <ElevatorMenu
          label="A"
          currentFloor={currentFloorA}
          elevatorState={elevatorAState}
        />
        <ElevatorMenu
          label="B"
          currentFloor={currentFloorB}
          elevatorState={elevatorBState}
        />
      </div>
    </div>
  );
}

export default MainPage;
