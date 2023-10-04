import { BUILDING_CONTROLLER_URL } from "../config";
import axios from "axios";
export const postInternalElevatorRequest = async (elevatorLabel, floor) => {
  return axios
    .post(
      `${BUILDING_CONTROLLER_URL}/addInternalRequest?elevatorLabel=${elevatorLabel}`,
      {
        destination: floor,
      }
    )
    .then((response) => {
      return `Destination ${response.data.destination} has been added for elevator ${elevatorLabel}!`;
    })
    .catch((error) => {
      console.error(error);
      return "Something went wrong!";
    });
};
