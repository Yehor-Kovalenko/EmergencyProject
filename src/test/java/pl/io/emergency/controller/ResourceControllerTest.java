package pl.io.emergency.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.io.emergency.service.Resource;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class ResourceControllerTest {

    @Autowired
    private ResourceController resourceController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateResource_Success() throws Exception {
        // Arrange
        Resource resourceRequest = new Resource("medical", "First aid kits", 50.0, "2");
        resourceRequest.setDate(LocalDate.now());
        resourceRequest.setStatus("0");

        // Perform the request and capture the response
        ResponseEntity<?> response = resourceController.createResource(resourceRequest);

        // Extract the response body as a Map
        Map<String, String> responseBody = (Map<String, String>) response.getBody();

        // Assert the response
        assertEquals("Resource successfully created", responseBody.get("message"));
        assertEquals("0", responseBody.get("status"));
    }

}
