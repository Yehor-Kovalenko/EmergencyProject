package pl.io.emergency.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ResourceTest {

    @Test
    public void first() {
        Resource r = new Resource();
        assertTrue(r.registerToDestination("type", "description", 20,1, 1));
    }
}
