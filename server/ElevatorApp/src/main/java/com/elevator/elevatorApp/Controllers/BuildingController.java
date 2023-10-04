package com.elevator.elevatorApp.Controllers;

import com.elevator.elevatorApp.Domain.ElevatorRequest;
import com.elevator.elevatorApp.Services.BuildingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/building")
public class BuildingController {
    private final BuildingService buildingService;

    BuildingController(BuildingService service){
        this.buildingService = service;
    }

    @PostMapping("addInternalRequest")
    public ResponseEntity<ElevatorRequest> addRequestElevatorA(@RequestBody ElevatorRequest request, @RequestParam String elevatorLabel){
       return new ResponseEntity<>(this.buildingService.addInternalElevatorRequest(request,elevatorLabel), HttpStatus.OK);
    }

    @PostMapping("addExternalRequest")
    public ResponseEntity<ElevatorRequest> addExternalRequest(@RequestBody ElevatorRequest request){
        return new ResponseEntity<>(this.buildingService.addExternalRequest(request),HttpStatus.OK);
    }

}
