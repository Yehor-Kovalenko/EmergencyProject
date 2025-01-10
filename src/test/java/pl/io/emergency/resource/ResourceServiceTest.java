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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

public class ResourceServiceTest {

    @Test
    public void CreateResourceToDestinationTest() {
        // Mock repository
        ResourceRepositorium resourceRepositorium = Mockito.mock(ResourceRepositorium.class);

        // ResourceService using mock repository
        ResourceService resourceService = new ResourceService(resourceRepositorium);

        // Input data
        ResourceType type = ResourceType.CLOTHES;
        String description = "First aid kits";
        double amount = 50.0;
        Long destinationId = 1L;
        Long holderId = 2L;

        // Mocked entity returned after saving
        ResourceEntity mockEntity = new ResourceEntity(type, description, amount, destinationId, holderId);
        mockEntity.setId(1L);

        // Define the save method to return mockEntity
        when(resourceRepositorium.save(any(ResourceEntity.class))).thenReturn(mockEntity);

        // Call the service method to save the resource
        ResourceDto resourceDto = resourceService.CreateResourceToDestination(type, description, amount, destinationId, holderId);

        // Assert the data in DTO is correct
        assertEquals(ResourceType.CLOTHES, resourceDto.getType());
        assertEquals("First aid kits", resourceDto.getDescription());
        assertEquals(50.0, resourceDto.getAmount());
        assertEquals(ResourceStatus.READY, resourceDto.getStatus());
        assertEquals(1L, resourceDto.getDestinationId());
        assertEquals(2L, resourceDto.getHolderId());

        // Verify the save method in repository was called once
        verify(resourceRepositorium, times(1)).save(any(ResourceEntity.class));
    }

    @Test
    public void CreateResourceToDonateTest() {
        // Mock repository
        ResourceRepositorium resourceRepositorium = Mockito.mock(ResourceRepositorium.class);

        // ResourceService using mock repository
        ResourceService resourceService = new ResourceService(resourceRepositorium);

        // Input data
        ResourceType type = ResourceType.CLOTHES;
        String description = "First aid kits";
        double amount = 50.0;
        Long holderId = 2L;

        // Mocked entity returned after saving
        ResourceEntity mockEntity = new ResourceEntity(type, description, amount, holderId);
        mockEntity.setId(1L);

        // Define the save method to return mockEntity
        when(resourceRepositorium.save(any(ResourceEntity.class))).thenReturn(mockEntity);

        // Call the service method to save the resource
        ResourceDto resourceDto = resourceService.CreateResourceToDonate(type, description, amount, holderId);

        // Assert the data in DTO is correct
        assertEquals(ResourceType.CLOTHES, resourceDto.getType());
        assertEquals("First aid kits", resourceDto.getDescription());
        assertEquals(50.0, resourceDto.getAmount());
        assertEquals(ResourceStatus.READY, resourceDto.getStatus());
        assertEquals(2L, resourceDto.getHolderId());

        // Verify the save method in repository was called once
        verify(resourceRepositorium, times(1)).save(any(ResourceEntity.class));
    }

    @Test
    public void findResourcesByHolderIdTest() {
        // Mock repository
        ResourceRepositorium resourceRepositorium = Mockito.mock(ResourceRepositorium.class);

        // ResourceService using mock repository
        ResourceService resourceService = new ResourceService(resourceRepositorium);

        // Test data preparation
        ResourceType type = ResourceType.CLOTHES;
        String description1 = "Winter jackets";
        double amount1 = 10.0;
        Long holderId = 2L;
        Long destinationId = 1L;

        ResourceEntity resource1 = new ResourceEntity(type, description1, amount1, destinationId, holderId);
        resource1.setId(1L);  // Set ID
        resource1.setDate_of_registration(LocalDate.now());

        String description2 = "First aid kits";
        double amount2 = 20.0;

        ResourceEntity resource2 = new ResourceEntity(type, description2, amount2, holderId);
        resource2.setId(2L);  // Set ID
        resource2.setDate_of_registration(LocalDate.now());

        // Mock repository method
        when(resourceRepositorium.findByHolderId(holderId)).thenReturn(Arrays.asList(resource1, resource2));

        // Call the service method
        List<ResourceDto> result = resourceService.findResourcesByHolderId(holderId);

        // Assert the correct number of resources
        assertEquals(2, result.size(), "Expected 2 resources for holderId=2");

        // Assert correct mapping of data
        assertEquals("Winter jackets", result.get(0).getDescription());
        assertEquals("First aid kits", result.get(1).getDescription());

        // Verify the findByHolderId method was called once
        verify(resourceRepositorium, times(1)).findByHolderId(holderId);
    }

    @Test
    public void findResourcesByDestinationIdTest() {
        // Mock repository
        ResourceRepositorium resourceRepositorium = Mockito.mock(ResourceRepositorium.class);

        // ResourceService using mock repository
        ResourceService resourceService = new ResourceService(resourceRepositorium);

        // Test data preparation
        ResourceType type = ResourceType.CLOTHES;
        String description1 = "Winter jackets";
        double amount1 = 10.0;
        Long destinationId = 1L;

        ResourceEntity resource1 = new ResourceEntity(type, description1, amount1, destinationId, 2L);
        resource1.setId(1L);  // Set ID
        resource1.setDate_of_registration(LocalDate.now());

        String description2 = "First aid kits";
        double amount2 = 20.0;

        ResourceEntity resource2 = new ResourceEntity(type, description2, amount2, destinationId, 3L);
        resource2.setId(2L);  // Set ID
        resource2.setDate_of_registration(LocalDate.now());

        // Mock repository method
        when(resourceRepositorium.findByDestinationId(destinationId)).thenReturn(Arrays.asList(resource1, resource2));

        // Call the service method
        List<ResourceDto> result = resourceService.findResourcesByDestinationId(destinationId);

        // Assert the correct number of resources
        assertEquals(2, result.size(), "Expected 2 resources for destinationId=1");

        // Assert correct mapping of data
        assertEquals("Winter jackets", result.get(0).getDescription());
        assertEquals("First aid kits", result.get(1).getDescription());

        // Verify the findByDestinationId method was called once
        verify(resourceRepositorium, times(1)).findByDestinationId(destinationId);
    }

    @Test
    public void convertToDtoTest() {
        // Mock repository
        ResourceRepositorium resourceRepositorium = Mockito.mock(ResourceRepositorium.class);

        // ResourceService using mock repository
        ResourceService resourceService = new ResourceService(resourceRepositorium);

        // Create a ResourceEntity instance for testing
        ResourceType type = ResourceType.CLOTHES;
        String description = "First aid kits";
        double amount = 50.0;
        Long destinationId = 1L;
        Long holderId = 2L;
        LocalDate dateOfRegistration = LocalDate.now();
        ResourceStatus status = ResourceStatus.READY;

        ResourceEntity resourceEntity = new ResourceEntity(type, description, amount, destinationId, holderId);
        resourceEntity.setId(1L);
        resourceEntity.setDate_of_registration(dateOfRegistration);
        resourceEntity.setResourceStatus(status);

        // Call convertToDto method
        ResourceDto resourceDto = resourceService.convertToDto(resourceEntity);

        // Assert correct mapping of data
        assertEquals(resourceEntity.getId(), resourceDto.getId());
        assertEquals(resourceEntity.getResourceType(), resourceDto.getType());
        assertEquals(resourceEntity.getDescription(), resourceDto.getDescription());
        assertEquals(resourceEntity.getAmount(), resourceDto.getAmount());
        assertEquals(resourceEntity.getDate_of_registration(), resourceDto.getDate());
        assertEquals(resourceEntity.getResourceStatus(), resourceDto.getStatus());
        assertEquals(resourceEntity.getDestinationId(), resourceDto.getDestinationId());
        assertEquals(resourceEntity.getHolderId(), resourceDto.getHolderId());
    }

    @Test
    public void testUpdateResourceStatus_Success() {
        // Mock repository
        ResourceRepositorium resourceRepositorium = Mockito.mock(ResourceRepositorium.class);

        // ResourceService using mock repository
        ResourceService resourceService = new ResourceService(resourceRepositorium);

        // Test data
        Long resourceId = 1L;
        ResourceStatus newStatus = ResourceStatus.DONATED;
        ResourceEntity existingResource = new ResourceEntity(ResourceType.CLOTHES, "First Aid Kit", 20.0, 1L, 2L);
        existingResource.setId(resourceId);

        // Mock repository method
        when(resourceRepositorium.findById(resourceId)).thenReturn(java.util.Optional.of(existingResource));

        // Call the service method
        ResourceDto updatedResourceDto = resourceService.updateResourceStatus(resourceId, newStatus);

        // Assert the status has been updated
        assertNotNull(updatedResourceDto);
        assertEquals(newStatus, updatedResourceDto.getStatus());

        // Verify the save method was called once with the updated resource
        verify(resourceRepositorium, times(1)).save(existingResource);
    }

    @Test
    public void testUpdateResourceStatus_ResourceNotFound() {
        // Mock repository
        ResourceRepositorium resourceRepositorium = Mockito.mock(ResourceRepositorium.class);

        // ResourceService using mock repository
        ResourceService resourceService = new ResourceService(resourceRepositorium);

        // Test data for a non-existing resource
        Long resourceId = 999L;
        ResourceStatus newStatus = ResourceStatus.DONATED;

        // Mock repository method to return empty Optional
        when(resourceRepositorium.findById(resourceId)).thenReturn(java.util.Optional.empty());

        // Call the service method
        ResourceDto updatedResource = resourceService.updateResourceStatus(resourceId, newStatus);

        // Assert that the result is null
        assertNull(updatedResource);
    }
}
