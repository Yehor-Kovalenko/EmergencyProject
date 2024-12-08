package pl.io.emergency.resource;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.io.emergency.entity.ResourceDto;
import pl.io.emergency.entity.ResourceEntity;
import pl.io.emergency.entity.ResourceStatus;
import pl.io.emergency.entity.ResourceType;
import pl.io.emergency.repository.ResourceRepositorium;
import pl.io.emergency.service.ResourceService;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class ResourceServiceTest {
    @Test
    public void CreateToDestinationTest() {
        // Tworzymy mocka repozytorium
        ResourceRepositorium resourceRepositorium = Mockito.mock(ResourceRepositorium.class);

        // Tworzymy obiekt serwisu, który używa tego repozytorium
        ResourceService resourceService = new ResourceService(resourceRepositorium);

        // Dane wejściowe
        ResourceType type = ResourceType.CLOTHES;
        String description = "First aid kits";
        double amount = 50.0;
        Long destinationId = 1L;
        Long holderId = 2L;

        // Tworzymy mockowaną encję, którą zwróci repozytorium po zapisie
        ResourceEntity mockEntity = new ResourceEntity(type, description, amount, destinationId, holderId);
        mockEntity.setId(1L);  // Ustawiamy ID, które będzie zwrócone

        // Określamy, że save() w repozytorium ma zwrócić mockEntity
        Mockito.when(resourceRepositorium.save(Mockito.any(ResourceEntity.class))).thenReturn(mockEntity);

        // Wywołujemy metodę serwisu, która zapisuje encję
        ResourceDto resourceDto = resourceService.CreateRespurceToDestination(type.name(), description, amount, destinationId, holderId);

        // Sprawdzamy, czy dane w obiekcie DTO są poprawne
        assertEquals(ResourceType.CLOTHES, resourceDto.getType());
        assertEquals("First aid kits", resourceDto.getDescription());
        assertEquals(50.0, resourceDto.getAmount());
        assertEquals(ResourceStatus.READY, resourceDto.getStatus());
        assertEquals(1L, resourceDto.getDestinationId());
        assertEquals(2L, resourceDto.getHolderId());

        // Sprawdzamy, czy metoda save() została wywołana w repozytorium
        Mockito.verify(resourceRepositorium, Mockito.times(1)).save(Mockito.any(ResourceEntity.class));

        // Sprawdzamy, czy repozytorium zwróciło dane, np. jeśli masz jakąś metodę do pobierania zapisanych encji
        // Możesz np. sprawdzić mocka za pomocą Mockito.verify
        Mockito.when(resourceRepositorium.findAll()).thenReturn(Arrays.asList(mockEntity));

        assertEquals(1, resourceRepositorium.findAll().size());
    }

    @Test
    public void CreateToDonateTest() {
        // Tworzymy mocka repozytorium
        ResourceRepositorium resourceRepositorium = Mockito.mock(ResourceRepositorium.class);

        // Tworzymy obiekt serwisu, który używa tego repozytorium
        ResourceService resourceService = new ResourceService(resourceRepositorium);

        // Dane wejściowe
        ResourceType type = ResourceType.CLOTHES;
        String description = "First aid kits";
        double amount = 50.0;
        Long holderId = 2L;

        // Tworzymy mockowaną encję, którą zwróci repozytorium po zapisie
        ResourceEntity mockEntity = new ResourceEntity(type, description, amount, holderId);
        mockEntity.setId(1L);  // Ustawiamy ID, które będzie zwrócone

        // Określamy, że save() w repozytorium ma zwrócić mockEntity
        Mockito.when(resourceRepositorium.save(Mockito.any(ResourceEntity.class))).thenReturn(mockEntity);

        // Wywołujemy metodę serwisu, która zapisuje encję
        ResourceDto resourceDto = resourceService.CreateResourceToDonate(type.name(), description, amount, holderId);

        // Sprawdzamy, czy dane w obiekcie DTO są poprawne
        assertEquals(ResourceType.CLOTHES, resourceDto.getType());
        assertEquals("First aid kits", resourceDto.getDescription());
        assertEquals(50.0, resourceDto.getAmount());
        assertEquals(ResourceStatus.READY, resourceDto.getStatus());
        assertEquals(2L, resourceDto.getHolderId());

        // Sprawdzamy, czy metoda save() została wywołana w repozytorium
        Mockito.verify(resourceRepositorium, Mockito.times(1)).save(Mockito.any(ResourceEntity.class));

        // Sprawdzamy, czy repozytorium zwróciło dane, np. jeśli masz jakąś metodę do pobierania zapisanych encji
        // Możesz np. sprawdzić mocka za pomocą Mockito.verify
        Mockito.when(resourceRepositorium.findAll()).thenReturn(Arrays.asList(mockEntity));

        assertEquals(1, resourceRepositorium.findAll().size());
    }

}
