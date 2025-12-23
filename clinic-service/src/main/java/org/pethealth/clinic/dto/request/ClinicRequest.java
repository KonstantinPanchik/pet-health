package org.pethealth.clinic.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClinicRequest {

    @Schema(description = "Название клиники", type = "string", example = "В мире животных")
    @NotBlank
    private String name;

    @Schema(description = "Город", type = "string", example = "Москва")
    @NotBlank
    private String city;

    @Schema(description = "Адресс",type = "string", example = "Улица Пушкина д.1")
    @NotBlank
    private String address;

    @Schema(description = "Телефон", type = "string",example = "+7(999)545-54-54")
    @NotBlank
    private String phone;
}
