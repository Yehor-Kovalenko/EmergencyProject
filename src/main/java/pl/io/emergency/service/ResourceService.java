package pl.io.emergency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.io.emergency.entity.ResourceStatus;
import pl.io.emergency.repository.ResourceRepositorium;
import pl.io.emergency.dto.ResourceDto;
import pl.io.emergency.entity.ResourceEntity;
import pl.io.emergency.entity.ResourceType;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResourceService {


    private final ResourceRepositorium resourceRepositorium;

    @Autowired
    public ResourceService(ResourceRepositorium resourceRepositorium) {
        this.resourceRepositorium = resourceRepositorium;
    }


    public ResourceDto CreateResourceToDestination(ResourceType type, String description, double amount, Long destinationId, Long holderId) {
        // Create the ResourceEntity that will be saved
        ResourceEntity resourceEntity = new ResourceEntity(type, description, amount, destinationId, holderId);

        // Save the entity to the repository
        ResourceEntity savedEntity = resourceRepositorium.save(resourceEntity);  // Save the entity

        // Create a ResourceDto from the saved entity
        ResourceDto resourceDto = new ResourceDto();
        resourceDto.setId(savedEntity.getId());
        resourceDto.setType(savedEntity.getResourceType());
        resourceDto.setDescription(savedEntity.getDescription());
        resourceDto.setAmount(savedEntity.getAmount());
        resourceDto.setDestinationId(savedEntity.getDestinationId());
        resourceDto.setHolderId(savedEntity.getHolderId());
        resourceDto.setStatus(ResourceStatus.READY);  // Set the status

        // Return the created ResourceDto
        return resourceDto;
    }




    public ResourceDto CreateResourceToDonate(ResourceType type, String description, double amount, Long holderId)
    {
        try {
            // Bezpośrednie użycie ResourceType zamiast String
            ResourceDto r = new ResourceDto(type, description, amount, holderId); // Tworzymy DTO
            ResourceEntity entity = new ResourceEntity(type, description, amount, holderId); // Tworzymy encję

            // Zapisujemy encję w repozytorium
            ResourceEntity entitySave =  resourceRepositorium.save(entity);
            r.setId(entitySave.getId());
            r.setDate(entitySave.getDate_of_registration());

            return r;
        } catch (Exception e) {
            // Obsługa wyjątków
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid: " + type, e);
        }
    }


    // Nowa metoda do pobierania zasobów według holderId
    public List<ResourceDto> findResourcesByHolderId(Long holderId) {
        try {
            // Wywołujemy metodę repozytorium, aby pobrać zasoby na podstawie holderId
            List<ResourceEntity> resources = resourceRepositorium.findByHolderId(holderId);

            // Konwertujemy listę encji na listę obiektów DTO
            return resources.stream()
                    .map(resource -> new ResourceDto(
                            resource.getId(),                            // id
                            resource.getResourceType(),                          // type
                            resource.getDescription(),                   // description
                            resource.getAmount(),                        // amount
                            resource.getDate_of_registration(),          // date
                            resource.getResourceStatus(),                        // status
                            resource.getDestinationId(),                 // destinationId
                            resource.getHolderId()                       // holderId
                    ))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // Obsługuje wyjątek, jeśli coś poszło nie tak
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching resources by holderId", e);
        }
    }

    public List<ResourceDto> findResourcesByDestinationId(Long destinationId) {
        try {
            // Wywołujemy metodę repozytorium, aby pobrać zasoby na podstawie destinationId
            List<ResourceEntity> resources = resourceRepositorium.findByDestinationId(destinationId);

            // Konwertujemy listę encji na listę obiektów DTO
            return resources.stream()
                    .map(resource -> new ResourceDto(
                            resource.getId(),                            // id
                            resource.getResourceType(),                          // type
                            resource.getDescription(),                   // description
                            resource.getAmount(),                        // amount
                            resource.getDate_of_registration(),          // date
                            resource.getResourceStatus(),                        // status
                            resource.getDestinationId(),                 // destinationId
                            resource.getHolderId()                       // holderId
                    ))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // Obsługuje wyjątek, jeśli coś poszło nie tak
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching resources by destinationId", e);
        }
    }

    // Konwersja z ResourceEntity na ResourceDto
    public ResourceDto convertToDto(ResourceEntity resourceEntity) {
        return new ResourceDto(
                resourceEntity.getId(),
                resourceEntity.getResourceType(),
                resourceEntity.getDescription(),
                resourceEntity.getAmount(),
                resourceEntity.getDate_of_registration(),
                resourceEntity.getResourceStatus(),
                resourceEntity.getDestinationId(),
                resourceEntity.getHolderId()
        );
    }

    public ResourceDto updateResourceStatus(Long resourceId, ResourceStatus status) {
        ResourceEntity resource = resourceRepositorium.findById(resourceId).orElse(null);
        if (resource == null) {
            return null;
        }
        resource.setResourceStatus(status);
        resourceRepositorium.save(resource);
        return convertToDto(resource);
    }

    public ResourceDto updateResourceDestination(Long resourceId, Long newDestinationId) {
        // Pobranie zasobu z repozytorium na podstawie resourceId
        ResourceEntity resource = resourceRepositorium.findById(resourceId).orElse(null);

        if (resource == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource with ID " + resourceId + " not found.");
        }

        // Aktualizacja destinationId
        resource.setDestinationId(newDestinationId);

        // Zapisanie zaktualizowanego zasobu w repozytorium
        ResourceEntity updatedResource = resourceRepositorium.save(resource);

        // Przekształcenie zaktualizowanej encji na DTO
        return convertToDto(updatedResource);
    }



}
