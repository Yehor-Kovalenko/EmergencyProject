package pl.io.emergency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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


    public ResourceDto CreateResourceToDestination(String type, String description, double amount, Long destinationId, Long holderId)
    {
        try {
            ResourceType paramType = ResourceType.valueOf(type);

            ResourceDto r = new ResourceDto(paramType, description, amount, destinationId, holderId);
            ResourceEntity entity = new ResourceEntity(paramType, description, amount, destinationId, holderId);

            ResourceEntity entitySave =  resourceRepositorium.save(entity);
            r.setId(entitySave.getId());
            r.setDate(entitySave.getDate_of_registration());

            return r;
        } catch (IllegalArgumentException e) {
            // Błąd w przypadku nieprawidłowego typu
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid type provided: " + type, e);
        } catch (Exception e) {
            // Obsługa innych wyjątków
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid: " + type, e);
        }
    }

    public ResourceDto CreateResourceToDonate(String type, String description, double amount, Long holderId)
    {
        try {
            ResourceType paramType = ResourceType.valueOf(type);
            ResourceDto r = new ResourceDto(paramType, description, amount, holderId);
            ResourceEntity entity = new ResourceEntity(paramType, description, amount, holderId);

            ResourceEntity entitySave =  resourceRepositorium.save(entity);
            r.setId(entitySave.getId());
            r.setDate(entitySave.getDate_of_registration());

            return r;
        } catch (IllegalArgumentException e) {
            // Błąd w przypadku nieprawidłowego typu
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid type provided: " + type, e);
        } catch (Exception e) {
            // Obsługa innych wyjątków
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
}
