package pl.io.emergency.resource;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import pl.io.emergency.controller.ResourceController;
import pl.io.emergency.dto.ResourceDto;
import pl.io.emergency.entity.ResourceStatus;
import pl.io.emergency.entity.ResourceType;
import pl.io.emergency.entity.ResourceEntity;

import pl.io.emergency.repository.ResourceRepositorium;
import pl.io.emergency.service.ResourceService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ResourceDtoControllerTest {

    @Autowired
    private ResourceController resourceController;

    @Test
    public void testCreateResourceToDestination_Success() throws Exception {
        // Przygotowanie danych wejściowych
        ResourceType type = ResourceType.CLOTHES;  // Enum ResourceType
        String description = "First aid kits";
        Double amount = 50.0;
        Long destinationId = 1L;
        Long holderId = 2L;

        // Mockowanie repozytorium
        ResourceRepositorium resourceRepositorium = Mockito.mock(ResourceRepositorium.class);
        ResourceService resourceService = new ResourceService(resourceRepositorium);
        ResourceController resourceController = new ResourceController(resourceService);

        // Mockowanie odpowiedzi z repozytorium
        ResourceEntity mockEntity = new ResourceEntity(type, description, amount, destinationId, holderId);
        mockEntity.setId(1L);  // Dodajemy ID do mockowanej encji
        when(resourceRepositorium.save(any(ResourceEntity.class))).thenReturn(mockEntity);  // Ustawiamy, żeby save zwracało mockEntity

        // Wywołanie metody
        ResponseEntity<ResourceDto> response = resourceController.createResourceToDestination(
                type, description, amount, destinationId, holderId);

        // Sprawdzanie odpowiedzi
        ResourceDto responseBody = response.getBody();
        assert responseBody != null;
        assertEquals(type, responseBody.getType());  // Porównanie z enumem
        assertEquals(description, responseBody.getDescription());
        assertEquals(amount, responseBody.getAmount());
        assertEquals(ResourceStatus.READY, responseBody.getStatus());  // Status READY
        assertEquals(destinationId, responseBody.getDestinationId());
        assertEquals(holderId, responseBody.getHolderId());
        assertEquals(1L, responseBody.getId());  // Sprawdzamy, czy ID zostało ustawione
    }

    @Test
    public void testCreateResourceToDonate_Success() throws Exception {
        // Przygotowanie danych wejściowych
        ResourceType type = ResourceType.CLOTHES;  // Używamy teraz ResourceType
        String description = "First aid kits";
        Double amount = 50.0;
        Long holderId = 2L;

        // Mockowanie repozytorium
        ResourceRepositorium resourceRepositorium = Mockito.mock(ResourceRepositorium.class);
        ResourceService resourceService = new ResourceService(resourceRepositorium);
        ResourceController resourceController = new ResourceController(resourceService);

        // Mockowanie odpowiedzi z repozytorium
        ResourceEntity mockEntity = new ResourceEntity(type, description, amount, holderId);
        mockEntity.setId(1L);  // Dodajemy ID do mockowanej encji
        when(resourceRepositorium.save(any(ResourceEntity.class))).thenReturn(mockEntity);  // Ustawiamy, żeby save zwracało mockEntity

        // Wywołanie metody
        ResponseEntity<ResourceDto> response = resourceController.createResourceToDonate(
                type, description, amount, holderId);

        // Sprawdzanie odpowiedzi
        ResourceDto responseBody = response.getBody();
        assert responseBody != null;
        assertEquals(type, responseBody.getType());  // Porównanie z enumem
        assertEquals(description, responseBody.getDescription());
        assertEquals(amount, responseBody.getAmount());
        assertEquals(ResourceStatus.READY, responseBody.getStatus());  // Status READY
        assertEquals(holderId, responseBody.getHolderId());
        assertEquals(1L, responseBody.getId());  // Sprawdzamy, czy ID zostało ustawione
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
        when(resourceRepositorium.findByHolderId(holderId)).thenReturn(Arrays.asList(new ResourceEntity(ResourceType.CLOTHES, "First aid kits", 50.0, 1L, holderId),
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
    public void testGetResourcesByHolderId_Failure() {
        // Ustalamy holderId, który nie istnieje
        Long holderId = 999L;

        // Mockowanie repozytorium, które zwróci pustą listę
        ResourceRepositorium resourceRepositorium = Mockito.mock(ResourceRepositorium.class);
        when(resourceRepositorium.findByHolderId(holderId)).thenReturn(Arrays.asList());

        // Mockowanie serwisu
        ResourceService resourceService = new ResourceService(resourceRepositorium);

        // Mockowanie kontrolera
        ResourceController resourceController = new ResourceController(resourceService);

        // Testowanie, czy metoda rzuca wyjątek ResponseStatusException
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            resourceController.getResourcesByHolderId(holderId);
        });

        // Sprawdzamy kod statusu i komunikat
        assertEquals(404, exception.getStatusCode().value());  // Oczekujemy kodu 404
        assertEquals("No resources found for holder with id: " + holderId, exception.getReason());  // Spodziewany komunikat wyjątku
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
        when(resourceRepositorium.findByDestinationId(destinationId)).thenReturn(Arrays.asList(
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

    @Test
    public void testGetResourcesByDestinationId_Failure() throws Exception {
        // Ustalamy destinationId, który nie istnieje
        Long destinationId = 999L;

        // Mockowanie repozytorium, które zwróci pustą listę
        ResourceRepositorium resourceRepositorium = Mockito.mock(ResourceRepositorium.class);
        when(resourceRepositorium.findByDestinationId(destinationId)).thenReturn(Arrays.asList());

        // Mockowanie serwisu
        ResourceService resourceService = new ResourceService(resourceRepositorium);

        // Mockowanie kontrolera
        ResourceController resourceController = new ResourceController(resourceService);

        // Testowanie, czy metoda rzuca wyjątek ResponseStatusException
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            resourceController.getResourcesByDestinationId(destinationId);
        });

        // Sprawdzamy kod statusu i komunikat
        assertEquals(404, exception.getStatusCode().value());  // Oczekujemy kodu 404
        assertEquals("No resources found for destinationId: " + destinationId, exception.getReason());  // Spodziewany komunikat wyjątku
    }

    @Test
    public void testUpdateResourceStatus_Success() throws Exception {
        // Tworzymy mocka repozytorium
        ResourceRepositorium resourceRepositorium = Mockito.mock(ResourceRepositorium.class);

        // Tworzymy obiekt serwisu
        ResourceService resourceService = new ResourceService(resourceRepositorium);

        // Tworzymy mocka kontrolera
        ResourceController resourceController = new ResourceController(resourceService);

        // Przygotowanie danych testowych
        Long resourceId = 1L;
        ResourceStatus newStatus = ResourceStatus.DONATED;
        ResourceEntity existingResource = new ResourceEntity(ResourceType.CLOTHES, "First Aid Kit", 20.0, 1L, 2L);
        existingResource.setId(resourceId);  // Ustawiamy ID zasobu

        // Mockowanie metody findById, aby zwróciła nasz istniejący zasób
        when(resourceRepositorium.findById(resourceId)).thenReturn(java.util.Optional.of(existingResource));

        // Mockowanie metody save, aby zwrócić zaktualizowany zasób
        when(resourceRepositorium.save(any(ResourceEntity.class))).thenReturn(existingResource);

        // Perform the request to update the resource status
        ResponseEntity<ResourceDto> response = resourceController.updateResourceStatus(resourceId, newStatus);

        // Assert the response
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());  // Spodziewamy się statusu 200 OK

        ResourceDto updatedResource = response.getBody();
        assert updatedResource != null;
        assertEquals(newStatus, updatedResource.getStatus());  // Sprawdzamy, czy status został zaktualizowany
        assertEquals(resourceId, updatedResource.getId());  // Sprawdzamy, czy zwrócony zasób ma poprawne ID
    }

    @Test
    public void testUpdateResourceStatus_ResourceNotFound() throws Exception {
        // Tworzymy mocka repozytorium
        ResourceRepositorium resourceRepositorium = Mockito.mock(ResourceRepositorium.class);

        // Tworzymy obiekt serwisu
        ResourceService resourceService = new ResourceService(resourceRepositorium);

        // Tworzymy mocka kontrolera
        ResourceController resourceController = new ResourceController(resourceService);

        // Przygotowanie danych testowych
        Long resourceId = 999L;  // Zasób, którego nie ma w bazie
        ResourceStatus newStatus = ResourceStatus.DONATED;

        // Mockowanie metody findById, która zwraca pustą wartość (brak zasobu)
        when(resourceRepositorium.findById(resourceId)).thenReturn(java.util.Optional.empty());

        // Perform the request to update the resource status
        ResponseEntity<ResourceDto> response = resourceController.updateResourceStatus(resourceId, newStatus);

        // Assert the response
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());  // Spodziewamy się statusu 404 Not Found
    }

}
