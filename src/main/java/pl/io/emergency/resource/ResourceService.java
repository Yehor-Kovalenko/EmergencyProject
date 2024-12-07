package pl.io.emergency.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceService {


    private final ResourceRepositorium resourceRepositorium;

    @Autowired
    public ResourceService(ResourceRepositorium resourceRepositorium) {
        this.resourceRepositorium = resourceRepositorium;
    }


    public ResourceDto CreateRespurceToDestination (ResourceType type, String description, double amount, Long destinationId, Long holderId)
    {
        ResourceDto r = new ResourceDto(type, description, amount, destinationId, holderId);
        ResourceEntity entity = new ResourceEntity(type, description, amount, destinationId, holderId);

        ResourceEntity entitySave =  resourceRepositorium.save(entity);
        r.setId(entitySave.getId());
        r.setDate(entitySave.getDate_of_registration());

        return r;
    }

    public ResourceDto CreateResourceToDonate(ResourceType type, String description, double amount, Long holderId)
    {
        ResourceDto r = new ResourceDto(type, description, amount, holderId);
        ResourceEntity entity = new ResourceEntity(type, description, amount, holderId);

        ResourceEntity entitySave =  resourceRepositorium.save(entity);
        r.setId(entitySave.getId());
        r.setDate(entitySave.getDate_of_registration());

        return r;
    }
}
