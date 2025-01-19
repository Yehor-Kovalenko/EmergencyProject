package pl.io.emergency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.io.emergency.entity.HelpRequestStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelpRequestDTOResponse {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String emailLanguage;
    private String description;
    private HelpRequestStatus status;
    private String uniqueCode;
    private LocalDateTime reportedDate;
}
