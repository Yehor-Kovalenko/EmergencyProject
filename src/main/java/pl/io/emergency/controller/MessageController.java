package pl.io.emergency.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.io.emergency.dto.messageDtoRead;
import pl.io.emergency.dto.messageDtoSend;
import pl.io.emergency.dto.searchDto;
import pl.io.emergency.entity.MessageEntity;
import pl.io.emergency.entity.User;
import pl.io.emergency.service.MessageService;
import pl.io.emergency.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inbox")
@Tag(name = "Messages", description = "Endpoints for managing messages")
public class MessageController {
    private final MessageService messageService;
    private final UserService userService;

    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @Operation(summary = "Sending message", description = "Endpoint for sending a new message")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Message sent successfully"),
    })
    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> sendMessage(@Valid @RequestBody messageDtoSend message) {

        boolean isSent = messageService.sendMessage(message.getSenderId(), message.getReceiverId(), message.getTitle(), message.getBody());
        Map<String, String> response = new HashMap<>();
        if (isSent) {
            response.put("message", "Message sent successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Failed to send message");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Operation(summary = "Get messages for a receiver", description = "Retrieve all messages for a specific receiver")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Messages retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No messages found for the given receiver ID"),
    })
    @PostMapping("/check")
    public ResponseEntity<List<MessageEntity>> getMessages(@Valid @RequestBody messageDtoRead message) {
        List<MessageEntity> messages = messageService.getMessages(message.getReceiverId());
        if (messages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(messages);
    }

    @Operation(summary = "Search users", description = "Endpoint for Retrieving all users for a specific string")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
    })
    @PostMapping("/search")
    public ResponseEntity<List<User>> getUsers(@Valid @RequestBody searchDto searchDto) {
        List<User> users = messageService.searchUsers(searchDto.getSearch());
        return ResponseEntity.ok(users);
    }
}
