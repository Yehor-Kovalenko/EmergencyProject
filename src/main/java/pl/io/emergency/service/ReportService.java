package pl.io.emergency.service;

import org.springframework.stereotype.Service;
import pl.io.emergency.entity.Report;
import pl.io.emergency.entity.ResourceEntity;
import pl.io.emergency.repository.ReportRepository;
import pl.io.emergency.repository.ResourceRepositorium;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final ResourceRepositorium resourceRepositorium;

    public ReportService(ReportRepository reportRepository, ResourceRepositorium resourceRepositorium) {
        this.reportRepository = reportRepository;
        this.resourceRepositorium = resourceRepositorium;
    }

    public List<Report> findAll() {
        return this.reportRepository.findAll();
    }

    public Optional<Report> findById(Long id) {
        return this.reportRepository.findById(id);
    }

    public void deleteById(Long id) {
        this.reportRepository.deleteById(id);
    }

    // Generates a report (Doesn't query the database!!!) for a given giverId
    public Report getGiverReport(Long giverId) {
        LocalDateTime localDateTime = LocalDateTime.now();

        // giverId in resources is not implemented yet, so ignore giverId and just return all resources
        // get all resources
         List<ResourceEntity> allResources = this.resourceRepositorium.findAll();

        return new Report(localDateTime, allResources);
    }
}
