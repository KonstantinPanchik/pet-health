package org.pethealth.users.controllers.imp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pethealth.users.dto.request.UserDataRequest;
import org.pethealth.users.dto.response.UserResponse;

import org.pethealth.users.controllers.UserController;
import org.pethealth.users.services.KeycloakApiService;
import org.pethealth.users.services.UserService;
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

    @PutMapping
    public UserResponse updateMe(@AuthenticationPrincipal Jwt jwt,
                                 @RequestBody UserDataRequest userDataRequest) {
        log.debug("REST PUT request to update user");
        return userService.updateMe(jwt, userDataRequest);
    }

    @PutMapping("/change-password")
    public void changePassword(@AuthenticationPrincipal Jwt jwt){
        log.debug("REST POST request to change password");
        keycloakApiService.changePassword(jwt);
    }

    @PutMapping("/verification-email")
    public void sendVerificationEmail(@AuthenticationPrincipal Jwt jwt){
        log.debug("REST POST request to send verification email");
        keycloakApiService.confirmEmail(jwt);
    }

}
