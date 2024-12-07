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


    public ResourceDto CreateRespurceToDestination (String type, String description, double amount, Long destinationId, Long holderId)
    {
        try {
            ResourceType paramType = ResourceType.valueOf(type);

            ResourceDto r = new ResourceDto(paramType, description, amount, destinationId, holderId);
            ResourceEntity entity = new ResourceEntity(paramType, description, amount, destinationId, holderId);

            ResourceEntity entitySave =  resourceRepositorium.save(entity);
            r.setId(entitySave.getId());
            r.setDate(entitySave.getDate_of_registration());

            return r;
        } catch (Exception e) {
            //System.out("Bad type");
            return null;
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
        } catch (Exception e) {
            //System.out("Bad type");
            return null;
        }
    }
}
