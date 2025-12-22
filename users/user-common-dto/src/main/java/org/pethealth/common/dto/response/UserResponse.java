package org.pethealth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.pethealth.common.dto.enums.SEX;


import java.time.LocalDate;

@Data
@Builder
public class UserResponse {

    @Schema(description = "Id пользователя в бд", type = "long", example = "1")
    private String id;

    @Schema(description = "username пользователя", type = "string", example = "i.ivanov")
    private String username;

    @Schema(description = "Имя пользователя", type = "string", example = "Иван")
    private String firstName;

    @Schema(description = "Фамилия пользователя", type = "string", example = "Иванов")
    private String lastName;

    @Schema(description = "email пользователя", type = "string", example = "Иванов")
    private String email;

    @Schema(description = "Город пользователя", type = "string", example = "Москва")
    private String city;

    @Schema(description = "Адресс", type = "string", example = "ул. Победы, д. 1, кв. 24")
    private String address;

    @Schema(description = "Телефон", type = "string", example = "+7(937)221-21-12")
    private String phone;

    @Schema(description = "Пол", type = "string", example = "MALE", allowableValues = {"MALE", "FEMALE"})
    private SEX sex;

    @Schema(description = "Дата рождения", type = "date", example = "2000-01-01")
    private LocalDate birthday;

    @Schema(description = "Верефицирован ли email",type = "boolean",example = "false")
    private Boolean isEmailVerified;
}
