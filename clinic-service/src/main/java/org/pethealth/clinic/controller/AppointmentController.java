package org.pethealth.clinic.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.pethealth.clinic.dto.aliases.AppointmentPageResponse;
import org.pethealth.clinic.dto.enums.AppointmentStatus;
import org.pethealth.clinic.dto.request.AppointmentCreationRequest;
import org.pethealth.clinic.dto.response.AppointmentResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Tag(name = "Записи на прием")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
        @ApiResponse(responseCode = "403", description = "Access Denied", content = @Content(
                schema = @Schema(example = "{\n\"statusCode\": 403,\"\n \"message\": \"Access Denied\"\n}")
        ))
})
@Validated
public interface AppointmentController {

    @Operation(summary = "Получение записи на прием по ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "OK", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AppointmentResponse.class)
            )),
            @ApiResponse(responseCode = "404", description = "Appointment not found", content = @Content(
                    schema = @Schema(example = "{\n\"statusCode\": 404,\"\n \"message\": \"Appointment not found\"\n}")
            ))
    })
    AppointmentResponse getAppointment(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long id
    );

    @Operation(summary = "Создание новой записи на прием")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "OK", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AppointmentResponse.class)
            )),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(
                    schema = @Schema(example = "{\n\"statusCode\": 400,\"\n \"message\": \"Неверный формат данных\"\n}")
            ))
    })
    AppointmentResponse createAppointment(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody @Validated AppointmentCreationRequest request
    );

    @Operation(summary = "Получение всех записей на прием по ID питомца")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "OK", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AppointmentPageResponse.class)
            ))
    })
    AppointmentPageResponse getAllAppointmentsByPet(
            @PathVariable Long petId,
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to,
            @RequestParam(required = false) AppointmentStatus status,
            Pageable pageable
    );

    @Operation(summary = "Получение всех записей на прием по ID клиники")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "OK", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AppointmentPageResponse.class)
            ))
    })
    AppointmentPageResponse getAllAppointmentsByClinicId(
            @PathVariable Long clinicId,
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to,
            @RequestParam(required = false) AppointmentStatus status,
            Pageable pageable
    );
}
