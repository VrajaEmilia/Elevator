import { BUILDING_CONTROLLER_URL } from "../config";
import axios from "axios";
export const postExternalElevatorRequest = async (floor) => {
  return axios
    .post(`${BUILDING_CONTROLLER_URL}/addExternalRequest`, {
      destination: floor,
    })
    .then((response) => {
      return `Request made for floor ${response.data.destination}`;
    })
    .catch((error) => {
      return "Something went wrong!";
    });
};
