package pl.io.emergency.resource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import pl.io.emergency.controller.ResourceController;
import pl.io.emergency.entity.ResourceDto;
import pl.io.emergency.entity.ResourceStatus;
import pl.io.emergency.entity.ResourceType;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ResourceDtoControllerTest {

    @Autowired
    private ResourceController resourceController;

    @Test
    public void testCreateResourceToDestination_Success() throws Exception {
        // Perform the request and capture the response
        ResponseEntity<ResourceDto> response = resourceController.createResourceToDestination(
                "CLOTHES", "First aid kits", 50.0, 1L, 2L);

        // Assert the response
        ResourceDto responseBody = response.getBody();
        assert responseBody != null;
        assertEquals(ResourceType.CLOTHES, responseBody.getType());
        assertEquals("First aid kits", responseBody.getDescription());
        assertEquals(50.0, responseBody.getAmount());
        assertEquals(ResourceStatus.READY, responseBody.getStatus());  // Zakładając, że status będzie READY
        assertEquals(1L, responseBody.getDestinationId());
        assertEquals(2L, responseBody.getHolderId());
    }

    @Test
    public void testCreateResourceToDonate_Success() throws Exception {
        // Perform the request and capture the response
        ResponseEntity<ResourceDto> response = resourceController.createResourceToDonate(
                "CLOTHES", "First aid kits", 50.0, 2L);

        // Assert the response
        ResourceDto responseBody = response.getBody();
        assert responseBody != null;
        assertEquals(ResourceType.CLOTHES, responseBody.getType());
        assertEquals("First aid kits", responseBody.getDescription());
        assertEquals(50.0, responseBody.getAmount());
        assertEquals(ResourceStatus.READY, responseBody.getStatus());  // Zakładając, że status będzie READY
        assertEquals(2L, responseBody.getHolderId());
    }
}
