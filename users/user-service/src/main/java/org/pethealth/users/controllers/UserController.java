package org.pethealth.users.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.pethealth.users.dto.request.UserDataRequest;
import org.pethealth.users.dto.response.UserResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Пользователи")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
        @ApiResponse(responseCode = "403", description = "Access Denied", content = @Content(
                schema = @Schema(example = "{\n\"statusCode\": 403,\"\n \"message\": \"Access Denied\"\n}")
        ))
})
@Validated
public interface UserController {

    @Operation(summary = "Получение информации о текущем пользователе")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "OK", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResponse.class)
            ))
    })
    UserResponse getMe(@AuthenticationPrincipal Jwt jwt);

    @Operation(summary = "Получение информации о текущем пользователе")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "OK", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResponse.class)
            )),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(
                    schema = @Schema(example = "{\n\"statusCode\": 400,\"\n \"message\": \"Неверное формат имени\"\n}")
            ))
    })
    UserResponse updateMe(@AuthenticationPrincipal Jwt jwt,
                          @RequestBody @Validated UserDataRequest userDataRequest);

}
