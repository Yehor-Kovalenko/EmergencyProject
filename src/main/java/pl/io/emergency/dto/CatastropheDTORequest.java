package pl.io.emergency.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatastropheDTORequest {

    @NotBlank
    private String type;

    private double longitude;
    private double latitude;
}
