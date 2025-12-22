package org.pethealth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;
import org.pethealth.common.dto.enums.SEX;

import java.time.LocalDate;

@Schema(description = "Запрос для обновления данных о пользователе")
@Getter
@Setter
public class UserDataRequest {

    @Schema(description = "Город", type = "string", example = "Тольятти")
    private String city;

    @Schema(description = "Адрес", type = "string", example = "ул. Победы, д.1, кв. 24")
    private String address;

    @Schema(description = "Телефон", type = "string", example = "+7(999)999-99-99")
    private String phone;

    @Schema(description = "Пол", type = "string", example = "MALE")
    private SEX sex;

    @Schema(description = "Дата рождения",type = "date", example = "2000-12-12")
    @Past
    private LocalDate birthday;
}
