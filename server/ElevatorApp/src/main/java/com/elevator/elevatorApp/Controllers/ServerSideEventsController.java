package com.elevator.elevatorApp.Controllers;

import com.elevator.elevatorApp.Services.BuildingService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/events")
public class ServerSideEventsController{
    private final BuildingService buildingService;

    public ServerSideEventsController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @GetMapping(path = "getSseEmitter", consumes = MediaType.ALL_VALUE)
    public SseEmitter getEmitter(@RequestParam String key){
        return buildingService.getEmitter(key);
    }

}
