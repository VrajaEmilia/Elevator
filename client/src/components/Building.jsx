import React, { createRef, useState } from "react";
import { Snackbar } from "@mui/material";
import "../styles/Building.css";
import Elevator from "./Elevator";
import { postExternalElevatorRequest } from "../api/externalElevatorRequest";
import { elevatorStateIcons } from "../config";
function Building({
  currentFloorA,
  currentFloorB,
  elevatorAState,
  elevatorBState,
}) {
  const floors = [...Array(7).keys()].map((_) => createRef());
  const [openSnackbar, setOpenSnackbar] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState("");

  const handleCallButton = async (destination) => {
    const response = await postExternalElevatorRequest(destination);
    setSnackbarMessage(response);
    setOpenSnackbar(true);
  };

  return (
    <div className="building-container">
      <div className="floors-container">
        {floors.map((floor, index) => (
          <div>
            <div className="floor" ref={floor}>
              <div className="elevator-states">
                <span className="state-container">
                  A:
                  <img
                    width={20}
                    height={20}
                    src={elevatorStateIcons[elevatorAState]}
                    alt="stateA"
                  />
                </span>
                <span className="state-container">
                  B:
                  <img
                    width={20}
                    height={20}
                    src={elevatorStateIcons[elevatorBState]}
                    alt="stateB"
                  />
                </span>
              </div>
              <button
                className="call-elevator-button"
                onClick={() => handleCallButton(index)}
              >
                Call
              </button>
            </div>
          </div>
        ))}
      </div>
      <Elevator floorsRef={floors} label="A" currentFloor={currentFloorA} />
      <Elevator floorsRef={floors} label="B" currentFloor={currentFloorB} />
      <Snackbar
        open={openSnackbar}
        onClose={() => setOpenSnackbar(false)}
        autoHideDuration={3000}
        message={snackbarMessage}
      />
    </div>
  );
}

export default Building;
