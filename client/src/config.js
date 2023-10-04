export const EVENTS_EMITTER_URL = "http://localhost:8080/events/getSseEmitter";
export const BUILDING_CONTROLLER_URL = "http://localhost:8080/building";

export const elevatorStates = {
  UP: "UP",
  DOWN: "DOWN",
  ARRIVED: "ARRIVED",
  WAITING: "WAITING",
};

export const elevatorStateIcons = {
  UP: "/arrow-up.svg",
  DOWN: "/arrow-down.svg",
  ARRIVED: "/ready.svg",
  WAITING: "./ready.svg",
};

export const elevatorStateMessages = {
  UP: "Going up!",
  DOWN: "Going down!",
  ARRIVED: "Arrived at destination",
  WAITING: "Waiting for requests...",
};
