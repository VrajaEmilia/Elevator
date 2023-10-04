import { useState, React } from "react";
import { postInternalElevatorRequest } from "../api/internalElevatorRequest";
import { Snackbar } from "@mui/material";
import { elevatorStateMessages } from "../config";

import "../styles/ElevatorMenu.css";

function ElevatorMenu({ label, currentFloor, elevatorState }) {
  const floors = Array.from(Array(7).keys());
  const [openSnackbar, setOpenSnackbar] = useState(false);
  const [openError, setOpenError] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState("");

  const handleMenuButtonClicked = async (floor) => {
    if (
      elevatorState === elevatorState.UP ||
      elevatorState === elevatorState.DOWN
    ) {
      setOpenError(true);
    } else {
      var responseMessage = await postInternalElevatorRequest(label, floor);
      setSnackbarMessage(responseMessage);
      setOpenSnackbar(true);
    }
  };

  return (
    <div className="menu-container">
      <h3>{label}</h3>
      <h5>Current floor: {currentFloor}</h5>
      <h6>{elevatorStateMessages[elevatorState]}</h6>
      <div className="buttons-container">
        {floors.map((floor) => (
          <button
            className="floor-button"
            onClick={() => handleMenuButtonClicked(floor)}
          >
            {floor}
          </button>
        ))}
      </div>
      <Snackbar
        open={openSnackbar}
        onClose={() => setOpenSnackbar(false)}
        autoHideDuration={3000}
        message={snackbarMessage}
      />
      <Snackbar
        open={openError}
        onClose={() => setOpenError(false)}
        autoHideDuration={3000}
        message="Only one destination can be chosen at once!"
      />
    </div>
  );
}

export default ElevatorMenu;
