package org.pethealth.clinic.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.pethealth.clinic.dto.enums.PetType;


@Getter
@Setter
public class PetRequest {

    @Schema(description = "Имя животного", type = "string", example = "Барсик")
    @NotBlank
    private String name;

    @Schema(description = "Порода животного", type = "string", example = "Сфинкс")
    private String breed;

    @Schema(description = "Описание питомца", type = "string", example = "Имеет проблемы с ногами")
    private String description;

    @Schema(description = "Год рождения питомца", type = "int", example = "2022")
    @Min(2000)
    @Max(2500)
    private Integer yearOfBirth;

    @Schema(description = "Тип животного", type = "string", example = "BIRD", oneOf = PetType.class)
    private PetType petType;
}
