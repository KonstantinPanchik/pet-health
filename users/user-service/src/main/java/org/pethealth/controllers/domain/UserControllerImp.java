package org.pethealth.controllers.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pethealth.common.dto.request.UserDataRequest;
import org.pethealth.common.dto.response.UserResponse;
import org.pethealth.controllers.domain.imp.UserController;

import org.pethealth.services.KeycloakApiService;
import org.pethealth.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserControllerImp implements UserController {

    private final UserService userService;
    private final KeycloakApiService keycloakApiService;

    @Override
    @GetMapping("/me")
    public UserResponse getMe(@AuthenticationPrincipal Jwt jwt) {
        log.debug("REST GET request to get user");
        return userService.getMe(jwt);
    }

    @PutMapping("/me")
    public UserResponse updateMe(@AuthenticationPrincipal Jwt jwt,
                                 @RequestBody UserDataRequest userDataRequest) {
        log.debug("REST PUT request to update user");
        return userService.updateMe(jwt, userDataRequest);
    }

    @PostMapping("/change-password")
    public void changePassword(@AuthenticationPrincipal Jwt jwt){
        log.debug("REST POST request to change password");
        keycloakApiService.changePassword(jwt);
    }

    @PostMapping("/send-verification-email")
    public void sendVerificationEmail(@AuthenticationPrincipal Jwt jwt){
        log.debug("REST POST request to send verification email");
        keycloakApiService.confirmEmail(jwt);
    }

}
