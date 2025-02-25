package pl.io.emergency.resource;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;

import org.springframework.beans.factory.annotation.Autowired;
import pl.io.emergency.dto.ResourceDto;
import pl.io.emergency.entity.ResourceEntity;
import pl.io.emergency.entity.ResourceStatus;
import pl.io.emergency.entity.ResourceType;
import pl.io.emergency.repository.ResourceRepositorium;
import pl.io.emergency.service.ResourceService;

public class ResourceRepositoriumTest {

    @Test
    public void testFindByHolderId() {
        // Tworzymy mocka repozytorium
        ResourceRepositorium resourceRepositorium = Mockito.mock(ResourceRepositorium.class);

        // Przygotowanie danych testowych
        ResourceEntity resource1 = new ResourceEntity(ResourceType.FOOD, "Canned food", 10.0, null, 2L);
        ResourceEntity resource2 = new ResourceEntity(ResourceType.CLOTHES, "Winter jackets", 5.0, null, 2L);
        ResourceEntity resource3 = new ResourceEntity(ResourceType.FOOD, "Tinned beans", 20.0, null, 3L);

        // Konfigurujemy mocka, aby zwracał odpowiednie zasoby, gdy wywołana zostanie metoda findByHolderId
        Mockito.when(resourceRepositorium.findByHolderId(2L)).thenReturn(Arrays.asList(resource1, resource2));
        Mockito.when(resourceRepositorium.findByHolderId(3L)).thenReturn(Arrays.asList(resource3));

        // Wywołanie metody repozytorium
        List<ResourceEntity> resourcesWithHolderId2 = resourceRepositorium.findByHolderId(2L);

        // Sprawdzamy, czy metoda zwróciła odpowiednią liczbę wyników
        assertEquals(2, resourcesWithHolderId2.size(), "Dla holderId=2, powinno zwrócić 2 zasoby.");

        // Sprawdzamy, czy wyniki mają poprawny holderId
        assertEquals(2L, resourcesWithHolderId2.get(0).getHolderId(), "Pierwszy zasób ma mieć holderId=2");
        assertEquals(2L, resourcesWithHolderId2.get(1).getHolderId(), "Drugi zasób ma mieć holderId=2");

        // Sprawdzamy, czy inne zasoby nie zostały uwzględnione
        List<ResourceEntity> resourcesWithHolderId3 = resourceRepositorium.findByHolderId(3L);
        assertEquals(1, resourcesWithHolderId3.size(), "Dla holderId=3, powinno zwrócić 1 zasób.");
        assertEquals(3L, resourcesWithHolderId3.get(0).getHolderId(), "Zasób ma mieć holderId=3");

        // Weryfikujemy, że metoda findByHolderId została wywołana odpowiednią ilość razy
        Mockito.verify(resourceRepositorium, Mockito.times(1)).findByHolderId(2L);
        Mockito.verify(resourceRepositorium, Mockito.times(1)).findByHolderId(3L);
    }
    @Test
    public void testFindByDestinationId() {
        // Tworzymy mocka repozytorium
        ResourceRepositorium resourceRepositorium = Mockito.mock(ResourceRepositorium.class);

        // Przygotowanie danych testowych
        ResourceEntity resource1 = new ResourceEntity(ResourceType.FOOD, "Canned food", 10.0, 1L, 2L);
        ResourceEntity resource2 = new ResourceEntity(ResourceType.CLOTHES, "Winter jackets", 5.0, 1L, 2L);
        ResourceEntity resource3 = new ResourceEntity(ResourceType.FOOD, "Tinned beans", 20.0, 3L, 2L);
        ResourceEntity resource4 = new ResourceEntity(ResourceType.CLOTHES, "T-Shirts", 15.0, 3L, 2L);

        // Konfigurujemy mocka, aby zwracał odpowiednie zasoby, gdy wywołana zostanie metoda findByDestinationId
        Mockito.when(resourceRepositorium.findByDestinationId(1L)).thenReturn(Arrays.asList(resource1, resource2));
        Mockito.when(resourceRepositorium.findByDestinationId(3L)).thenReturn(Arrays.asList(resource3, resource4));

        // Wywołanie metody repozytorium
        List<ResourceEntity> resourcesWithDestinationId1 = resourceRepositorium.findByDestinationId(1L);

        // Sprawdzamy, czy metoda zwróciła odpowiednią liczbę wyników
        assertEquals(2, resourcesWithDestinationId1.size(), "Dla destinationId=1, powinno zwrócić 2 zasoby.");

        // Sprawdzamy, czy wyniki mają poprawny destinationId
        assertEquals(1L, resourcesWithDestinationId1.get(0).getDestinationId(), "Pierwszy zasób ma mieć destinationId=1");
        assertEquals(1L, resourcesWithDestinationId1.get(1).getDestinationId(), "Drugi zasób ma mieć destinationId=1");

        // Sprawdzamy, czy inne zasoby nie zostały uwzględnione
        List<ResourceEntity> resourcesWithDestinationId3 = resourceRepositorium.findByDestinationId(3L);
        assertEquals(2, resourcesWithDestinationId3.size(), "Dla destinationId=3, powinno zwrócić 2 zasoby.");
        assertEquals(3L, resourcesWithDestinationId3.get(0).getDestinationId(), "Zasób ma mieć destinationId=3");
        assertEquals(3L, resourcesWithDestinationId3.get(1).getDestinationId(), "Zasób ma mieć destinationId=3");

        // Weryfikujemy, że metoda findByDestinationId została wywołana odpowiednią ilość razy
        Mockito.verify(resourceRepositorium, Mockito.times(1)).findByDestinationId(1L);
        Mockito.verify(resourceRepositorium, Mockito.times(1)).findByDestinationId(3L);
    }
    @Test
    public void testFindById() {
        // Tworzymy mocka repozytorium
        ResourceRepositorium resourceRepositorium = Mockito.mock(ResourceRepositorium.class);

        // Przygotowanie danych testowych (utworzenie zasobów z przypisaniem id przez save)
        ResourceEntity resource1 = new ResourceEntity(ResourceType.FOOD, "Canned food", 10.0, 1L, 2L);
        resource1.setId(1L);  // Przypisanie id ręcznie (symulacja zachowania z serwisu)

        ResourceEntity resource2 = new ResourceEntity(ResourceType.CLOTHES, "Winter jackets", 5.0, 1L, 2L);
        resource2.setId(2L);  // Przypisanie id ręcznie (symulacja zachowania z serwisu)

        // Konfigurujemy mocka, aby zwracał odpowiednie zasoby dla id
        Mockito.when(resourceRepositorium.findById(1L)).thenReturn(Optional.of(resource1));
        Mockito.when(resourceRepositorium.findById(2L)).thenReturn(Optional.of(resource2));
        Mockito.when(resourceRepositorium.findById(999L)).thenReturn(Optional.empty());

        // Scenariusz 1: Sprawdzamy, czy zasób o id=1 (resource1) został poprawnie znaleziony
        Optional<ResourceEntity> foundResource1 = resourceRepositorium.findById(1L);
        assertEquals(true, foundResource1.isPresent(), "Zasób o id=1 powinien być znaleziony.");
        assertEquals("Canned food", foundResource1.get().getDescription(), "Zasób o id=1 powinien mieć opis 'Canned food'");

        // Scenariusz 2: Sprawdzamy, czy zasób o id=2 (resource2) został poprawnie znaleziony
        Optional<ResourceEntity> foundResource2 = resourceRepositorium.findById(2L);
        assertEquals(true, foundResource2.isPresent(), "Zasób o id=2 powinien być znaleziony.");
        assertEquals("Winter jackets", foundResource2.get().getDescription(), "Zasób o id=2 powinien mieć opis 'Winter jackets'");

        // Scenariusz 3: Sprawdzamy, czy zasób o id=999 (którego nie ma) nie został znaleziony
        Optional<ResourceEntity> foundResource999 = resourceRepositorium.findById(999L);
        assertEquals(false, foundResource999.isPresent(), "Zasób o id=999 powinien być niezdefiniowany.");

        // Weryfikujemy, że metoda findById została wywołana z oczekiwanymi argumentami
        Mockito.verify(resourceRepositorium, Mockito.times(1)).findById(1L);  // Weryfikacja dla id=1
        Mockito.verify(resourceRepositorium, Mockito.times(1)).findById(2L);  // Weryfikacja dla id=2
        Mockito.verify(resourceRepositorium, Mockito.times(1)).findById(999L);  // Weryfikacja dla id=999

        // Dodatkowa weryfikacja: upewniamy się, że inne ID nie zostały przypadkowo sprawdzone
        Mockito.verify(resourceRepositorium, Mockito.times(0)).findById(3L);  // Sprawdzamy, czy nie wywołano z id=3
    }




}
