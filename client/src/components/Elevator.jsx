import React, { useEffect, useState } from "react";
import "../styles/Elevator.css";
function Elevator({ label, currentFloor, floorsRef }) {
  const [position, setPosition] = useState({
    top: 0,
    left: 0,
  });
  useEffect(() => {
    setPosition({
      top: floorsRef[currentFloor].current.offsetTop + 5,
      left:
        floorsRef[currentFloor].current.offsetLeft +
        5 +
        (label == "B" ? 80 : 0),
    });
  }, [currentFloor]);
  return (
    <div className="elevator" style={position}>
      <label>Lift {label}</label>
    </div>
  );
}

export default Elevator;
