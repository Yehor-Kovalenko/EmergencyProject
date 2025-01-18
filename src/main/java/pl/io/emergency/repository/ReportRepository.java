package pl.io.emergency.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.io.emergency.entity.Report;
import pl.io.emergency.entity.ReportType;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

//    @Query("select c from ExampleEntity c where c.name = :name")
//    Optional<ExampleEntity> findByName(@Param("name") String name);
    public default void saveReport(Report report) {
        this.save(report);
    }

    public default List<Report> loadGovernmentReport(ReportType reportType, LocalDate dateFrom, LocalDate dateTo) {
        return this.findAll().stream()
                .filter(report -> !report.getDateFrom().isBefore(dateFrom) && !report.getDateTo().isAfter(dateTo)
                        && report.getReportType() == reportType)
                .collect(Collectors.toList());
    }

    public default Report loadGiverReport(Long id) {
        return this.findById(id).orElse(null);
    }
}

