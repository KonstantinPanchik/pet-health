package org.pethealth.clinic.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.pethealth.clinic.dto.enums.PetType;

@Data
@Builder
@Schema(description = "Краткая информация о питомце")
public class PetShortResponse {

    @Schema(description = "Id питомца в бд", type = "long", example = "1")
    private Long id;

    @Schema(description = "Имя питомца", type = "string", example = "Барсик")
    private String name;

    @Schema(description = "Тип животного", type = "string", example = "CAT", allowableValues = {"CAT", "DOG", "BIRD", "FISH"})
    private PetType petType;
}
