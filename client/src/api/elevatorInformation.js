import { EVENTS_EMITTER_URL, elevatorStates } from "../config";
import { fetchEventSource } from "@microsoft/fetch-event-source";
export const setElevatorInformation = async (
  elevatorLabel,
  setCurrentFloor,
  setElevatorState
) => {
  await fetchEventSource(`${EVENTS_EMITTER_URL}?key=${elevatorLabel}`, {
    method: "GET",
    headers: {
      Accept: "text/event-stream",
    },
    onmessage(event) {
      const message = JSON.parse(event.data);
      if (message.currentFloor !== undefined)
        setCurrentFloor(message.currentFloor);
      if (message.state) setElevatorState(elevatorStates[message.state]);
    },
    onclose() {
      console.log("Connection closed by the server");
    },
    onerror(err) {
      console.log("There was an error from server", err);
    },
  });
};
