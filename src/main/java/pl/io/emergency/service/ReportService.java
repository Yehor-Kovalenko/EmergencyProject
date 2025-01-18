package pl.io.emergency.service;

import org.springframework.stereotype.Service;
import pl.io.emergency.entity.*;
import pl.io.emergency.repository.CatastropheRepository;
import pl.io.emergency.repository.ReportRepository;
import pl.io.emergency.repository.ResourceRepositorium;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final ResourceRepositorium resourceRepositorium;
    private final CatastropheRepository catastropheRepositorium;

    public ReportService(ReportRepository reportRepository,
                         ResourceRepositorium resourceRepositorium,
                         CatastropheRepository catastropheRepositorium) {
        this.reportRepository = reportRepository;
        this.resourceRepositorium = resourceRepositorium;
        this.catastropheRepositorium = catastropheRepositorium;
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
         List<ResourceEntity> allResources = this.resourceRepositorium.findByHolderId(giverId);

        return new Report(localDateTime, allResources);
    }

    public Report getGovernmentReport(ReportType reportType, LocalDate dateFrom, LocalDate dateTo) {
        switch (reportType) {
            case ACTIVE_CATASTROPHES -> {
                List<Catastrophe> allActiveCatastrophes = this.catastropheRepositorium.findAll()
                        .stream()
                        .filter(Catastrophe::isActive)
                        .toList();
                return new Report(reportType, LocalDateTime.now(), dateFrom, dateTo, allActiveCatastrophes);
            }
            case ARCHIVE_CATASTROPHES -> {
                List<Catastrophe> allArchivedCatastrophes = this.catastropheRepositorium.findAll()
                        .stream()
                        .filter(catastrophe -> !catastrophe.isActive())
                        .toList();
                return new Report(reportType, LocalDateTime.now(), dateFrom, dateTo, allArchivedCatastrophes);
            }
            // Everything that is not DELIVERED or ENROUTE is an "active" resource
            case ACTIVE_NGO_RESOURCES -> {
                List<ResourceEntity> allActiveResources = this.resourceRepositorium.findAll()
                        .stream()
                        .filter(resource -> resource.getResourceStatus() != ResourceStatus.DELIVERED
                                && resource.getResourceStatus() != ResourceStatus.ENROUTE)
                        .toList();
                return new Report(reportType, LocalDateTime.now(), dateFrom, dateTo, allActiveResources);
            }
            case ARCHIVE_NGO_RESOURCES -> {
                List<ResourceEntity> allArchivedResources = this.resourceRepositorium.findAll()
                        .stream()
                        .filter(resource -> resource.getResourceStatus() == ResourceStatus.DELIVERED
                                || resource.getResourceStatus() == ResourceStatus.ENROUTE)
                        .toList();
                return new Report(reportType, LocalDateTime.now(), dateFrom, dateTo, allArchivedResources);
            }
            default -> {
                return new Report(reportType, LocalDateTime.now(), dateFrom, dateTo, null);
            }
        }
    }
}
