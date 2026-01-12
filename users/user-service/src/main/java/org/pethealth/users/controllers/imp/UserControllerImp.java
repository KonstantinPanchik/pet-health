package org.pethealth.users.controllers.imp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pethealth.users.dto.request.UserDataRequest;
import org.pethealth.users.dto.response.UserResponse;

import org.pethealth.users.controllers.UserController;
import org.pethealth.users.services.KeycloakApiService;
import org.pethealth.users.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users/me")
@RequiredArgsConstructor
public class UserControllerImp implements UserController {

    private final UserService userService;
    private final KeycloakApiService keycloakApiService;

    @Override
    @GetMapping
    public UserResponse getMe(@AuthenticationPrincipal Jwt jwt) {
        log.debug("REST GET request to get user");
        return userService.getMe(jwt);
    }

    @Override
    @PutMapping
    public UserResponse updateMe(@AuthenticationPrincipal Jwt jwt,
                                 @RequestBody UserDataRequest userDataRequest) {
        log.debug("REST PUT request to update user");
        return userService.updateMe(jwt, userDataRequest);
    }

    @Override
    @PutMapping("/change-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@AuthenticationPrincipal Jwt jwt){
        log.debug("REST PUT request to change password");
        keycloakApiService.changePassword(jwt);
    }

    @Override
    @PutMapping("/verification-email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendVerificationEmail(@AuthenticationPrincipal Jwt jwt){
        log.debug("REST PUT request to send verification email");
        keycloakApiService.confirmEmail(jwt);
    }

}
