package pl.io.emergency.service;

import org.springframework.stereotype.Service;
import pl.io.emergency.entity.ExampleEntity;
import pl.io.emergency.repository.ExampleRepository;

import java.util.List;
import java.util.Optional;

/**
 * Przykladowa implementacja serwisu wykorzystającego funkcjonalność ExampleRepository
 */
@Service
public class ExampleService {
    private final ExampleRepository exampleRepository;

    public ExampleService(ExampleRepository exampleRepository) {
        this.exampleRepository = exampleRepository;
    }

    public List<ExampleEntity> findAll() {
        return this.exampleRepository.findAll();
    }

    public Optional<ExampleEntity> findById(Long id) {
        return this.exampleRepository.findById(id);
    }

    public void deleteById(Long id) {
        this.exampleRepository.deleteById(id);
    }
}
