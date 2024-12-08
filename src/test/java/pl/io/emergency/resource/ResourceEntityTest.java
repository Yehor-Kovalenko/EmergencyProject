package pl.io.emergency.resource;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.io.emergency.entity.ResourceEntity;
import pl.io.emergency.entity.ResourceType;
import pl.io.emergency.repository.ResourceRepositorium;

import static org.junit.jupiter.api.Assertions.*;
public class ResourceEntityTest {
    @Test
    public void testSaveEntity() {
        // Tworzymy mocka repozytorium
        ResourceRepositorium resourceRepositorium = Mockito.mock(ResourceRepositorium.class);

        // Tworzymy przykładową encję
        ResourceEntity entity = new ResourceEntity();
        entity.setResourceType(ResourceType.CLOTHES);
        entity.setDescription("Test description");
        entity.setAmount(10.0);

        // Tworzymy obiekt encji, który ma być zwrócony po zapisaniu
        ResourceEntity savedEntity = new ResourceEntity();
        savedEntity.setId(1L);  // Ustawiamy wygenerowane ID
        savedEntity.setResourceType(ResourceType.CLOTHES);
        savedEntity.setDescription("Test description");
        savedEntity.setAmount(10.0);

        // Określamy, że po wywołaniu save() z mocka, ma zostać zwrócony savedEntity
        Mockito.when(resourceRepositorium.save(entity)).thenReturn(savedEntity);

        // Wywołanie metody save()
        ResourceEntity result = resourceRepositorium.save(entity);

        // Sprawdzenie, czy ID zostało wygenerowane
        assertNotNull(result.getId(), "ID powinno zostać wygenerowane");
        assertEquals(1L, result.getId(), "ID powinno wynosić 1");

        // Sprawdzamy, czy encja została zapisana i czy zwrócony obiekt to expected savedEntity
        assertEquals(savedEntity, result, "Zapisana encja powinna być zgodna z oczekiwaną");
    }


}
