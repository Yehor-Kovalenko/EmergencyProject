package pl.io.emergency.service;

import org.springframework.stereotype.Service;
import pl.io.emergency.dto.VolunteerWithActionsDto;
import pl.io.emergency.entity.*;
import pl.io.emergency.repository.*;
import pl.io.emergency.entity.users.Volunteer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final ResourceRepositorium resourceRepositorium;
    private final CatastropheRepository catastropheRepositorium;
    private final VolunteerRepository volunteerRepository;
    private final ActionRepository actionRepository;

    public ReportService(ReportRepository reportRepository,
                         ResourceRepositorium resourceRepositorium,
                         CatastropheRepository catastropheRepositorium,
                         VolunteerRepository volunteerRepository,
                         ActionRepository actionRepository) {
        this.reportRepository = reportRepository;
        this.resourceRepositorium = resourceRepositorium;
        this.catastropheRepositorium = catastropheRepositorium;
        this.volunteerRepository = volunteerRepository;
        this.actionRepository = actionRepository;
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
        Report maybeOldReport = this.reportRepository.loadGovernmentReport(reportType, dateFrom, dateTo).stream().findFirst().orElse(null);
        if (maybeOldReport == null) {
            maybeOldReport = null;
        }
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
                        .filter(catastrophe -> !catastrophe.isActive()
                                && (catastrophe.getReportedDate().isAfter(dateFrom.atStartOfDay())
                                && catastrophe.getReportedDate().isBefore(dateTo.plusDays(1).atStartOfDay())))
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
                        .filter(resource -> (resource.getResourceStatus() == ResourceStatus.DELIVERED
                                || resource.getResourceStatus() == ResourceStatus.ENROUTE)
                                && resource.getDate_of_registration().atStartOfDay().isAfter(dateFrom.atStartOfDay())
                                && resource.getDate_of_registration().atStartOfDay().isBefore(dateTo.plusDays(1).atStartOfDay()))
                        .toList();
                return new Report(reportType, LocalDateTime.now(), dateFrom, dateTo, allArchivedResources);
            }
            case ACTIVE_VOLUNTEERS -> {
                List<VolunteerWithActionsDto> volunteersWithActions = this.getVolunteersWithActions(false);
                return new Report(reportType, LocalDateTime.now(), dateFrom, dateTo, volunteersWithActions);
            }
            case ARCHIVE_VOLUNTEERS -> {
                List<VolunteerWithActionsDto> volunteersWithActions = this.getVolunteersWithActions(true);
                return new Report(reportType, LocalDateTime.now(), dateFrom, dateTo, volunteersWithActions);
            }
            default -> {
                return new Report(reportType, LocalDateTime.now(), dateFrom, dateTo, null);
            }
        }
    }

    public List<VolunteerWithActionsDto> getVolunteersWithActions(boolean available) {
        // Fetch all volunteers filtered by availability
        List<Volunteer> volunteers = volunteerRepository.findAll()
                .stream()
                .filter(volunteer -> volunteer.isAvailable() == available)
                .collect(Collectors.toList());
        
        // Map each volunteer to VolunteerWithActionsDTO
        return volunteers.stream()
                .map(volunteer -> {
                    // Fetch actions associated with this volunteer by their ID
                    List<Action> actions = actionRepository.findByVolunteerId(volunteer.getId());
                    return new VolunteerWithActionsDto(volunteer, actions);
                })
                .collect(Collectors.toList());
    }

}
