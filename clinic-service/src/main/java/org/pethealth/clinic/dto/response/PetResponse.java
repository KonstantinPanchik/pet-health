package org.pethealth.clinic.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.pethealth.clinic.dto.enums.PetType;

@Builder
@Getter
@Setter
@Schema(description = "Полная информация о питомце")
public class PetResponse {

    @Schema(description = "Id питомца в бд", type = "long", example = "1")
    private Long id;

    @Schema(description = "Имя питомца", type = "string", example = "Барсик")
    private String name;

    @Schema(description = "Порода питомца", type = "string", example = "Сфинкс")
    private String breed;

    @Schema(description = "Описание питомца", type = "string", example = "Имеет проблемы с ногами")
    private String description;

    @Schema(description = "Год рождения питомца", type = "integer", example = "2022")
    private Integer yearOfBirth;

    @Schema(description = "Тип животного", type = "string", example = "CAT", allowableValues = {"CAT", "DOG", "BIRD", "FISH"})
    private PetType petType;

    @Schema(description = "Id владельца питомца", type = "string", example = "user-uuid-123")
    private String ownerId;

}
