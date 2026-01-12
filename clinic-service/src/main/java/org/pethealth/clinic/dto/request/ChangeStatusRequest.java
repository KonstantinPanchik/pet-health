package org.pethealth.clinic.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.pethealth.clinic.dto.enums.AppointmentStatus;

@Schema(description = "Запрос на изменение статуса")
@Getter
@Setter
public class ChangeStatusRequest {

    @Schema(description = "Статус для приема", type = "string", example = "CANCELLED")
    @NotNull
    private AppointmentStatus status;
}
