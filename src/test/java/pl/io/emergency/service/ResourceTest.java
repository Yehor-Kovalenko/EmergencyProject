package pl.io.emergency.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

//@SpringBootTest
//@ContextConfiguration(classes = {Resource.class, S3ClientConfig.class})
public class ResourceTest {

    @Test
    public void testResourceConstructor1() {
        String type = "medical";
        String description = "First aid kits";
        double amount = 50.0;
        String destinationId = "1";
        String holderId = "2";

        // Utworzenie instancji klasy Resource z dynamicznymi danymi
        Resource resource = new Resource(type, description, amount, destinationId, holderId);

        // Testowanie czy wartości zostały poprawnie ustawione
        assertEquals("medical", resource.getType());
        assertEquals("First aid kits", resource.getDescription());
        assertEquals(50.0, resource.getAmount());
        assertEquals(LocalDate.now(),resource.getDate());
        assertEquals("0",resource.getStatus());
        assertEquals("1", resource.getDestinationId());
        assertEquals("2", resource.getHolderId());
    }

    @Test
    public void testResourceConstructor2() {
        String type = "medical";
        String description = "First aid kits";
        double amount = 50.0;
        String holderId = "2";

        // Utworzenie instancji klasy Resource z dynamicznymi danymi
        Resource resource = new Resource(type, description, amount, holderId);

        // Testowanie czy wartości zostały poprawnie ustawione
        assertEquals("medical", resource.getType());
        assertEquals("First aid kits", resource.getDescription());
        assertEquals(LocalDate.now(),resource.getDate());
        assertEquals("0",resource.getStatus());
        assertEquals(50.0, resource.getAmount());
        assertEquals(null, resource.getDestinationId());
        assertEquals("2", resource.getHolderId());
    }
}
