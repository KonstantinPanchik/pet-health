package org.pethealth.clinic.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Schema(description = "Клиника")
public class ClinicResponse {

    @Schema(description = "Id клиники в бд", type = "long", example = "1")
    private Long id;

    @Schema(description = "Название клиники", type = "string", example = "Ветеринарная клиника 'Дружок'")
    private String name;

    @Schema(description = "Город", type = "string", example = "Москва")
    private String city;

    @Schema(description = "Адрес клиники", type = "string", example = "ул. Ленина, д. 10")
    private String address;

    @Schema(description = "Телефон клиники", type = "string", example = "+7(495)123-45-67")
    private String phone;

    @Schema(description = "Id владельца клиники", type = "string", example = "user-uuid-123")
    private String ownerId;

}
