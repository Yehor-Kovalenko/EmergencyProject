package pl.io.emergency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelpRequestDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String description;
    private String uniqueCode;
    private LocalDateTime reportedDate;
}
