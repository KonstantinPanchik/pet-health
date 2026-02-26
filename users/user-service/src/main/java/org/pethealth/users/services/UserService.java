package org.pethealth.users.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.pethealth.notifications.dto.service.NotificationQueueMessageService;
import org.pethealth.users.dto.request.UserDataRequest;
import org.pethealth.users.dto.request.UserUpdateAccountRequest;
import org.pethealth.users.dto.response.UserResponse;
import org.pethealth.users.entities.User;
import org.pethealth.users.mappers.UserMapper;
import org.pethealth.users.repository.UserRepository;
import org.pethealth.security.converter.JwtUserConverter;
import org.pethealth.security.model.JwtUserPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUserConverter jwtUserConverter;
    private final RealmResource realmKeycloak;
    private final NotificationQueueMessageService notificationQueueMessageService;

    @Transactional
    public UserResponse getMe(Jwt jwt) {
        JwtUserPrincipal jwtUserPrincipal = jwtUserConverter.convert(jwt);
        Optional<User> byId = userRepository.findById(jwtUserPrincipal.getSub());

        User user;
        if (byId.isEmpty()){
            log.debug("No user with username {}", jwtUserPrincipal.getUsername());
            user = new User();
            user.setId(jwtUserPrincipal.getSub());
            user.setUsername(jwtUserPrincipal.getUsername());
            user.setEmail(jwtUserPrincipal.getEmail());
            user.setFirstName(jwtUserPrincipal.getFirstName());
            user.setLastName(jwtUserPrincipal.getLastName());
            User save = userRepository.save(user);

            notificationQueueMessageService.sendMessage(userMapper.toUserEvent(save));
            log.debug("Saved user with username {}", jwtUserPrincipal.getUsername());
        }else {
            user = byId.get();
        }

        UserResponse userResponse = userMapper.toUserResponse(user);
        userResponse.setIsEmailVerified(jwtUserPrincipal.getEmailVerified());
        return userResponse;
    }

    @Transactional
    public UserResponse updateMe(Jwt jwt, UserDataRequest userDataRequest) {
        JwtUserPrincipal jwtUserPrincipal = jwtUserConverter.convert(jwt);
        User user = userRepository.findById(jwtUserPrincipal.getSub())
                .orElseThrow(() -> new RuntimeException("No user with username " + jwtUserPrincipal.getUsername()));//TODO переделеать ошибку

        if (userDataRequest.getCity() != null) {
            log.debug("Updating user with city {}", userDataRequest.getCity());
            user.setCity(userDataRequest.getCity());
        }

        if (userDataRequest.getAddress() != null) {
            log.debug("Updating user with address {}", userDataRequest.getAddress());
            user.setAddress(userDataRequest.getAddress());
        }

        if (userDataRequest.getPhone() != null) {
            log.debug("Updating user with phone {}", userDataRequest.getPhone());
            user.setPhone(userDataRequest.getPhone());
        }

        if (userDataRequest.getSex() != null) {
            log.debug("Updating user with sex {}", userDataRequest.getSex());
            user.setSex(userDataRequest.getSex());
        }
        if (userDataRequest.getBirthday() != null) {
            log.debug("Updating user with birthday {}", userDataRequest.getBirthday());
            user.setBirthday(userDataRequest.getBirthday());
        }

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void confirmEmail(Jwt jwt) {
        JwtUserPrincipal principal = jwtUserConverter.convert(jwt);
        if (principal.getEmailVerified()) {
            throw new RuntimeException("Email already verified");//todo ошибка переделать
        }
        realmKeycloak.users().get(principal.getSub()).sendVerifyEmail();
        log.debug("Email for verification was sent");
    }

    public void changePassword(Jwt jwt) {
        JwtUserPrincipal principal = jwtUserConverter.convert(jwt);
        if (!principal.getEmailVerified()) {
            throw new RuntimeException("Email not verified");//todo ошибка переделать
        }

        realmKeycloak.users()
                .get(principal.getSub())
                .executeActionsEmail(List.of("UPDATE_PASSWORD"));

        log.info("Password changed for user {}", principal.getUsername());
    }

    public UserResponse updateEmailFirstNameAndLastName(Jwt jwt, UserUpdateAccountRequest request) {
        JwtUserPrincipal principal = jwtUserConverter.convert(jwt);
        if (!principal.getEmailVerified()) {
            throw new RuntimeException("Email not verified");//todo ошибка переделать
        }
        UserResource userResource = realmKeycloak.users().get(principal.getSub());
        User user = userRepository.findById(principal.getSub())
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserRepresentation representation = userResource.toRepresentation();

        if (request.getFirstName() != null) {
            representation.setFirstName(request.getFirstName());
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            representation.setLastName(request.getLastName());
            user.setLastName(request.getLastName());

        }
        if (request.getEmail() != null) {
            representation.setEmail(request.getEmail());
            representation.setEmailVerified(false);

        }
        userResource.update(representation);
        User savedUser = userRepository.save(user);
        notificationQueueMessageService.sendMessage(userMapper.toUserEvent(savedUser));
        return userMapper.toUserResponse(savedUser);
    }


}
