package org.pethealth.clinic.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.pethealth.clinic.dto.enums.AppointmentStatus;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@Schema(description = "Встреча")
public class AppointmentResponse {

    @Schema(description = "Id клиники в бд", type = "long", example = "1")
    private Long id;

    @Schema(description = "Причина записи", type = "string", example = "Стерилизация")
    private String reason;

    @Schema(description = "Животное записанное в клинику", implementation = PetShortResponse.class)
    private PetShortResponse pet;

    @Schema(description = "Клиника в которую записано животное", implementation = ClinicResponse.class)
    private ClinicResponse clinic;

    @Schema(description = "Дата и время на которое записанно животное", type = "date")
    private LocalDateTime dateTime;

    @Schema(description = "Статус записи", type = "string", example = "CANCELLED")
    private AppointmentStatus status;
}
