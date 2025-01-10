package pl.io.emergency.resource;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

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
}
