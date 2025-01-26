package pl.io.emergency.service;

import org.springframework.stereotype.Service;

import pl.io.emergency.entity.users.Official;

import pl.io.emergency.repository.OfficialRepository;

import java.util.List;


@Service

public class OfficialService {
    private final OfficialRepository officialRepository;

    public OfficialService(OfficialRepository officialRepository) {
        this.officialRepository = officialRepository;
    }
    public List<Official> findAll() {
        return officialRepository.findAll();
    }
}

