package pl.io.emergency.resource;

import org.junit.jupiter.api.Test;
import pl.io.emergency.entity.ResourceDto;
import pl.io.emergency.entity.ResourceStatus;
import pl.io.emergency.entity.ResourceType;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
//@ContextConfiguration(classes = {Resource.class, S3ClientConfig.class})
public class ResourceDtoTest {

    @Test
    public void testResourceConstructor1() {
        ResourceType type = ResourceType.CLOTHES;
        String description = "First aid kits";
        double amount = 50.0;
        Long destinationId = 1L;
        Long holderId = 2L;

        // Utworzenie instancji klasy Resource z dynamicznymi danymi
        ResourceDto resourceDto = new ResourceDto(type, description, amount, destinationId, holderId);

        // Testowanie czy wartości zostały poprawnie ustawione
        assertEquals(ResourceType.CLOTHES, resourceDto.getType());
        assertEquals("First aid kits", resourceDto.getDescription());
        assertEquals(50.0, resourceDto.getAmount());
        assertEquals(ResourceStatus.READY, resourceDto.getStatus());
        assertEquals(1L, resourceDto.getDestinationId());
        assertEquals(2L, resourceDto.getHolderId());
    }

    @Test
    public void testResourceConstructor2() {
        ResourceType type = ResourceType.CLOTHES;
        String description = "First aid kits";
        double amount = 50.0;
        Long holderId = 2L;

        // Utworzenie instancji klasy Resource z dynamicznymi danymi
        ResourceDto resourceDto = new ResourceDto(type, description, amount, holderId);

        // Testowanie czy wartości zostały poprawnie ustawione
        assertEquals(ResourceType.CLOTHES, resourceDto.getType());
        assertEquals("First aid kits", resourceDto.getDescription());
        assertEquals(ResourceStatus.READY, resourceDto.getStatus());
        assertEquals(50.0, resourceDto.getAmount());
        assertEquals(null, resourceDto.getDestinationId());
        assertEquals(2L, resourceDto.getHolderId());
    }
}
