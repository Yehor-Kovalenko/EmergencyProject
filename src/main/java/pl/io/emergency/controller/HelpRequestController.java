package pl.io.emergency.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.io.emergency.dto.HelpRequestDTOResponse;
import pl.io.emergency.dto.HelpRequestDTORequest;
import pl.io.emergency.entity.HelpRequest;
import pl.io.emergency.service.EventService;

@RestController
@RequestMapping("/api/help-requests")
public class HelpRequestController {

    @Autowired
    private EventService eventService;

    @Operation(summary = "Create a new help request", description = "Creates a new help request for a specified catastrophe")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Help request created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/catastrophe/{catastropheId}")
    public ResponseEntity<HelpRequest> createHelpRequest(
            @PathVariable Long catastropheId,
            @Valid @RequestBody HelpRequestDTORequest helpRequestDTORequest) {
        HelpRequest helpRequest = mapFromDTORequest(helpRequestDTORequest);
        HelpRequest savedHelpRequest = eventService.createHelpRequest(catastropheId, helpRequest);
        return new ResponseEntity<>(savedHelpRequest, HttpStatus.CREATED);
    }

    @Operation(summary = "Retrieve a help request by its unique code", description = "Fetches a help request using its unique identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Help request found"),
            @ApiResponse(responseCode = "404", description = "Help request not found")
    })
    @GetMapping("/{uniqueCode}")
    public ResponseEntity<HelpRequest> getHelpRequestByUniqueCode(@PathVariable String uniqueCode) {
        return eventService.getHelpRequestByUniqueCode(uniqueCode)
                .map(helpRequest -> new ResponseEntity<>(helpRequest, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Update an existing help request", description = "Updates details of an existing help request identified by its unique code")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Help request updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Help request not found")
    })
    @PutMapping("/{uniqueCode}")
    public ResponseEntity<HelpRequestDTOResponse> updateHelpRequest(
            @PathVariable String uniqueCode,
            @Valid @RequestBody HelpRequestDTORequest updateDTO) {
        HelpRequest updatedHelpRequest = eventService.updateHelpRequest(uniqueCode, updateDTO);
        HelpRequestDTOResponse dto = mapToDTOResponse(updatedHelpRequest);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    private HelpRequest mapFromDTORequest(HelpRequestDTORequest helpRequestDTORequest) {
        HelpRequest helpRequest = new HelpRequest();
        helpRequest.setFirstName(helpRequestDTORequest.getFirstName());
        helpRequest.setLastName(helpRequestDTORequest.getLastName());
        helpRequest.setEmail(helpRequestDTORequest.getEmail());
        helpRequest.setEmailLanguage(helpRequestDTORequest.getEmailLanguage());
        helpRequest.setDescription(helpRequestDTORequest.getDescription());
        return helpRequest;
    }

    private HelpRequestDTOResponse mapToDTOResponse(HelpRequest helpRequest) {
        HelpRequestDTOResponse dto = new HelpRequestDTOResponse();
        dto.setId(helpRequest.getId());
        dto.setFirstName(helpRequest.getFirstName());
        dto.setLastName(helpRequest.getLastName());
        dto.setEmail(helpRequest.getEmail());
        dto.setEmailLanguage(helpRequest.getEmailLanguage());
        dto.setDescription(helpRequest.getDescription());
        dto.setStatus(helpRequest.getStatus());
        dto.setUniqueCode(helpRequest.getUniqueCode());
        dto.setReportedDate(helpRequest.getReportedDate());
        return dto;
    }
}