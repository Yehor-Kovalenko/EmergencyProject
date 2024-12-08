package pl.io.emergency.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.io.emergency.entity.Catastrophe;
import pl.io.emergency.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/api/catastrophes")
public class CatastropheController {

    @Autowired
    private EventService eventService;

    @Operation(summary = "Get all catastrophes", description = "Retrieves a list of all recorded catastrophes")
    @GetMapping
    public List<Catastrophe> getAllCatastrophes() {
        return eventService.getAllCatastrophes();
    }

    @Operation(summary = "Create a new catastrophe", description = "Creates a new catastrophe with the provided details")
    @PostMapping
    public ResponseEntity<Catastrophe> createCatastrophe(@Valid @RequestBody Catastrophe catastrophe) {
        Catastrophe savedReport = eventService.createCatastrophe(catastrophe);
        return new ResponseEntity<>(savedReport, HttpStatus.CREATED);
    }
}
