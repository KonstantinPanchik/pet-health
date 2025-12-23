package org.pethealth.clinic.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.pethealth.clinic.dto.request.PetRequest;
import org.pethealth.clinic.dto.response.PetResponse;
import org.pethealth.clinic.dto.response.PetShortResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Питомцы")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
        @ApiResponse(responseCode = "403", description = "Access Denied", content = @Content(
                schema = @Schema(example = "{\n\"statusCode\": 403,\"\n \"message\": \"Access Denied\"\n}")
        ))
})
@Validated
public interface PetController {

    @Operation(summary = "Создание нового питомца")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "OK", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PetResponse.class)
            )),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(
                    schema = @Schema(example = "{\n\"statusCode\": 400,\"\n \"message\": \"Неверный формат данных\"\n}")
            ))
    })
    PetResponse createPet(@AuthenticationPrincipal Jwt jwt,
                         @RequestBody @Validated PetRequest petRequest);

    @Operation(summary = "Получение списка питомцев текущего пользователя")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "OK", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PetShortResponse.class)
            ))
    })
    List<PetShortResponse> getMyPets(@AuthenticationPrincipal Jwt jwt);

    @Operation(summary = "Получение информации о питомце по ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "OK", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PetResponse.class)
            )),
            @ApiResponse(responseCode = "404", description = "Pet not found", content = @Content(
                    schema = @Schema(example = "{\n\"statusCode\": 404,\"\n \"message\": \"Pet not found\"\n}")
            )),
            @ApiResponse(responseCode = "403", description = "Access Denied - не являетесь владельцем питомца", content = @Content(
                    schema = @Schema(example = "{\n\"statusCode\": 403,\"\n \"message\": \"Pet owner is not the owner of the pet\"\n}")
            ))
    })
    PetResponse getPetById(@AuthenticationPrincipal Jwt jwt,
                          @PathVariable Long petId);

    @Operation(summary = "Обновление информации о питомце")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "OK", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PetResponse.class)
            )),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(
                    schema = @Schema(example = "{\n\"statusCode\": 400,\"\n \"message\": \"Неверный формат данных\"\n}")
            )),
            @ApiResponse(responseCode = "404", description = "Pet not found", content = @Content(
                    schema = @Schema(example = "{\n\"statusCode\": 404,\"\n \"message\": \"Pet not found\"\n}")
            )),
            @ApiResponse(responseCode = "403", description = "Access Denied - не являетесь владельцем питомца", content = @Content(
                    schema = @Schema(example = "{\n\"statusCode\": 403,\"\n \"message\": \"Pet owner is not the owner of the pet\"\n}")
            ))
    })
    PetResponse updatePet(@AuthenticationPrincipal Jwt jwt,
                         @PathVariable Long petId,
                         @RequestBody @Validated PetRequest petRequest);
}

