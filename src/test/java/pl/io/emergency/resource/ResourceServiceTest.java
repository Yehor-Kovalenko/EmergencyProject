package pl.io.emergency.resource;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.io.emergency.dto.ResourceDto;
import pl.io.emergency.entity.ResourceEntity;
import pl.io.emergency.entity.ResourceStatus;
import pl.io.emergency.entity.ResourceType;
import pl.io.emergency.repository.ResourceRepositorium;
import pl.io.emergency.service.ResourceService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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
        ResourceDto resourceDto = resourceService.CreateResourceToDestination(type.name(), description, amount, destinationId, holderId);

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

    @Test
    public void findResourcesByHolderIdTest() {
        // Tworzymy mocka repozytorium
        ResourceRepositorium resourceRepositorium = Mockito.mock(ResourceRepositorium.class);

        // Tworzymy obiekt serwisu, który używa tego repozytorium
        ResourceService resourceService = new ResourceService(resourceRepositorium);

        // Przygotowanie danych testowych
        ResourceType type = ResourceType.CLOTHES;
        String description1 = "Winter jackets";
        double amount1 = 10.0;
        Long holderId = 2L;
        Long destinationId = 1L;

        ResourceEntity resource1 = new ResourceEntity(type, description1, amount1, destinationId, holderId);
        resource1.setId(1L);  // Ustawiamy ID
        resource1.setDate_of_registration(LocalDate.now());

        String description2 = "First aid kits";
        double amount2 = 20.0;

        ResourceEntity resource2 = new ResourceEntity(type, description2, amount2, holderId);
        resource2.setId(2L);  // Ustawiamy ID
        resource2.setDate_of_registration(LocalDate.now());

        // Zamockowanie repozytorium
        Mockito.when(resourceRepositorium.findByHolderId(holderId)).thenReturn(Arrays.asList(resource1, resource2));

        // Wywołanie metody serwisowej
        List<ResourceDto> result = resourceService.findResourcesByHolderId(holderId);

        // Sprawdzamy, czy wynik jest zgodny z oczekiwaniami
        assertEquals(2, result.size(), "Powinna być 2 zasoby dla holderId=2");

        // Sprawdzamy, czy dane są poprawnie mapowane
        assertEquals("Winter jackets", result.get(0).getDescription(), "Pierwszy zasób ma być 'Winter jackets'");
        assertEquals("First aid kits", result.get(1).getDescription(), "Drugi zasób ma być 'First aid kits'");
        assertEquals(1L, result.get(0).getId(), "ID pierwszego zasobu");
        assertEquals(2L, result.get(1).getId(), "ID drugiego zasobu");

        // Weryfikujemy, czy repozytorium zostało wywołane z odpowiednim parametrem
        Mockito.verify(resourceRepositorium, Mockito.times(1)).findByHolderId(holderId);
    }
}
