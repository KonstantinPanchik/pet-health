package org.pethealth.clinic.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.pethealth.clinic.dto.aliases.ClinicPageResponse;
import org.pethealth.clinic.dto.request.ClinicRequest;
import org.pethealth.clinic.dto.response.ClinicResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Клиники")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
        @ApiResponse(responseCode = "403", description = "Access Denied", content = @Content(
                schema = @Schema(example = "{\n\"statusCode\": 403,\"\n \"message\": \"Access Denied\"\n}")
        ))
})
@Validated
public interface ClinicController {

    @Operation(summary = "Создание новой клиники")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "OK", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClinicResponse.class)
            )),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(
                    schema = @Schema(example = "{\n\"statusCode\": 400,\"\n \"message\": \"Неверный формат данных\"\n}")
            ))
    })
    ClinicResponse createClinic(@AuthenticationPrincipal Jwt jwt,
                                @RequestBody @Validated ClinicRequest clinicRequest);

    @Operation(summary = "Получение списка клиник текущего пользователя")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "OK", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClinicResponse.class)
            ))
    })
    List<ClinicResponse> getMyClinics(@AuthenticationPrincipal Jwt jwt);

    @Operation(summary = "Получение информации о клинике по ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "OK", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClinicResponse.class)
            )),
            @ApiResponse(responseCode = "404", description = "Clinic not found", content = @Content(
                    schema = @Schema(example = "{\n\"statusCode\": 404,\"\n \"message\": \"Clinic not found\"\n}")
            )),
            @ApiResponse(responseCode = "403", description = "Access Denied - не являетесь владельцем клиники", content = @Content(
                    schema = @Schema(example = "{\n\"statusCode\": 403,\"\n \"message\": \"Clinic owner is not the owner of the clinic\"\n}")
            ))
    })
    ClinicResponse getClinicById(@PathVariable Long clinicId);

    @Operation(summary = "Обновление информации о клинике")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "OK", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClinicResponse.class)
            )),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(
                    schema = @Schema(example = "{\n\"statusCode\": 400,\"\n \"message\": \"Неверный формат данных\"\n}")
            )),
            @ApiResponse(responseCode = "404", description = "Clinic not found", content = @Content(
                    schema = @Schema(example = "{\n\"statusCode\": 404,\"\n \"message\": \"Clinic not found\"\n}")
            ))
    })
    ClinicResponse updateClinic(@AuthenticationPrincipal Jwt jwt,
                               @PathVariable Long clinicId,
                               @RequestBody @Validated ClinicRequest clinicRequest);

    @Operation(summary = "Поиск клиник по названию")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "OK", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClinicPageResponse.class)
            ))
    })
    ClinicPageResponse searchClinics(@RequestParam String name, Pageable pageable);
}

