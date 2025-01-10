package pl.io.emergency.resource;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import pl.io.emergency.controller.ResourceController;
import pl.io.emergency.dto.ResourceDto;
import pl.io.emergency.entity.ResourceStatus;
import pl.io.emergency.entity.ResourceType;
import pl.io.emergency.entity.ResourceEntity;

import pl.io.emergency.repository.ResourceRepositorium;
import pl.io.emergency.service.ResourceService;

import java.util.Arrays;
import java.util.List;

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

    @Test
    public void testGetResourcesByHolderId_Success() throws Exception {
        // Ustalamy holderId
        Long holderId = 2L;

        // Tworzymy zasoby przed testowaniem
        ResourceDto resource1 = new ResourceDto(ResourceType.CLOTHES, "First aid kits", 50.0, 1L, holderId);
        ResourceDto resource2 = new ResourceDto(ResourceType.FOOD, "Canned food", 30.0, holderId);

        // Mockowanie repozytorium, żeby zwróciło zasoby
        ResourceRepositorium resourceRepositorium = Mockito.mock(ResourceRepositorium.class);
        Mockito.when(resourceRepositorium.findByHolderId(holderId)).thenReturn(Arrays.asList(new ResourceEntity(ResourceType.CLOTHES, "First aid kits", 50.0, 1L, holderId),
                new ResourceEntity(ResourceType.FOOD, "Canned food", 30.0, holderId)));

        // Mockowanie serwisu
        ResourceService resourceService = new ResourceService(resourceRepositorium);

        // Mockowanie kontrolera
        ResourceController resourceController = new ResourceController(resourceService);

        // Perform the request to fetch resources for holderId 2
        ResponseEntity<List<ResourceDto>> response = resourceController.getResourcesByHolderId(holderId);

        // Assert the response
        List<ResourceDto> responseBody = response.getBody();
        assert responseBody != null;
        assertEquals(2, responseBody.size());  // Spodziewamy się dwóch zasobów dla holderId 2

        // Sprawdzamy szczegóły zasobów
        assertEquals(ResourceType.CLOTHES, responseBody.get(0).getType());
        assertEquals("First aid kits", responseBody.get(0).getDescription());
        assertEquals(50.0, responseBody.get(0).getAmount());
        assertEquals(ResourceStatus.READY, responseBody.get(0).getStatus());
        assertEquals(1L, responseBody.get(0).getDestinationId());
        assertEquals(holderId, responseBody.get(0).getHolderId());

        assertEquals(ResourceType.FOOD, responseBody.get(1).getType());
        assertEquals("Canned food", responseBody.get(1).getDescription());
        assertEquals(30.0, responseBody.get(1).getAmount());
        assertEquals(ResourceStatus.READY, responseBody.get(1).getStatus());
        assertEquals(null, responseBody.get(1).getDestinationId());
        assertEquals(holderId, responseBody.get(1).getHolderId());
    }

    @Test
    public void testGetResourcesByDestinationId_Success() throws Exception {
        // Ustalamy destinationId
        Long destinationId = 1L;

        // Tworzymy zasoby przed testowaniem
        ResourceDto resource1 = new ResourceDto(ResourceType.CLOTHES, "First aid kits", 50.0, destinationId, 2L);
        ResourceDto resource2 = new ResourceDto(ResourceType.FOOD, "Canned food", 30.0, destinationId, 2L);

        // Mockowanie repozytorium, żeby zwróciło zasoby powiązane z destinationId
        ResourceRepositorium resourceRepositorium = Mockito.mock(ResourceRepositorium.class);
        Mockito.when(resourceRepositorium.findByDestinationId(destinationId)).thenReturn(Arrays.asList(
                new ResourceEntity(ResourceType.CLOTHES, "First aid kits", 50.0, destinationId, 2L),
                new ResourceEntity(ResourceType.FOOD, "Canned food", 30.0, destinationId, 2L)
        ));

        // Mockowanie serwisu
        ResourceService resourceService = new ResourceService(resourceRepositorium);

        // Mockowanie kontrolera
        ResourceController resourceController = new ResourceController(resourceService);

        // Perform the request to fetch resources for destinationId 1
        ResponseEntity<List<ResourceDto>> response = resourceController.getResourcesByDestinationId(destinationId);

        // Assert the response
        List<ResourceDto> responseBody = response.getBody();
        assert responseBody != null;
        assertEquals(2, responseBody.size());  // Spodziewamy się dwóch zasobów dla destinationId 1

        // Sprawdzamy szczegóły zasobów
        assertEquals(ResourceType.CLOTHES, responseBody.get(0).getType());
        assertEquals("First aid kits", responseBody.get(0).getDescription());
        assertEquals(50.0, responseBody.get(0).getAmount());
        assertEquals(ResourceStatus.READY, responseBody.get(0).getStatus());
        assertEquals(destinationId, responseBody.get(0).getDestinationId());
        assertEquals(2L, responseBody.get(0).getHolderId());

        assertEquals(ResourceType.FOOD, responseBody.get(1).getType());
        assertEquals("Canned food", responseBody.get(1).getDescription());
        assertEquals(30.0, responseBody.get(1).getAmount());
        assertEquals(ResourceStatus.READY, responseBody.get(1).getStatus());
        assertEquals(destinationId, responseBody.get(1).getDestinationId());
        assertEquals(2L, responseBody.get(1).getHolderId());
    }
}
