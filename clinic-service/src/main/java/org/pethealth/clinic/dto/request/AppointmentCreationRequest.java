package org.pethealth.clinic.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "Запрос на создание встречи")
public class AppointmentCreationRequest {

    @NotNull
    @Schema(description = "Id питомца", type = "long", example = "13")
    private Long petId;

    @Schema(description = "Id клиники", type = "long", example = "124")
    private Long clinicId;

    @Schema(description = "Дата и время записи", type = "date", example = "2026-01-21T13:00")
    @Future
    private LocalDateTime dateTime;

    @Schema(description = "Причина", type = "string", example = "Стерилизация")
    private String reason;
}
