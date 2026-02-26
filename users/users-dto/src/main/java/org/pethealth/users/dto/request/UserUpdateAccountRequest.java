package org.pethealth.users.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Запрос для обновления главных данных о пользователе")
@Getter
@Setter
public class UserUpdateAccountRequest {

    @Schema(description = "Имя пользователя", type = "string", example = "Иван")
    @Pattern(regexp = "^[А-ЯЁ][а-яё]+$",
            message = "Имя должно начинаться с заглавной буквы и содержать только русские буквы")
    private String firstName;

    @Schema(description = "Фамилия пользователя", type = "string", example = "Декорсия")
    @Pattern(regexp = "^[А-ЯЁ][а-яё]+$",
            message = "Фамилия должна начинаться с заглавной буквы и содержать только русские буквы")
    private String lastName;

    @Schema(description = "Электронная почта пользователя", type = "string", example = "good@mail.ru")
    @Email
    private String email;
}
