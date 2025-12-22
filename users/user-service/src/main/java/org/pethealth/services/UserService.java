package org.pethealth.services;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pethealth.dto.request.UserDataRequest;
import org.pethealth.dto.response.UserResponse;
import org.pethealth.entities.User;
import org.pethealth.mappers.UserMapper;
import org.pethealth.repository.domain.UserRepository;
import org.pethealth.security.converter.JwtUserConverter;
import org.pethealth.security.model.JwtUserPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUserConverter jwtUserConverter;

    @Transactional
    public UserResponse getMe(Jwt jwt) {
        JwtUserPrincipal jwtUserPrincipal = jwtUserConverter.convert(jwt);
        Optional<User> byUsername = userRepository.findById(jwtUserPrincipal.getSub());

        if (byUsername.isPresent()) {
            log.debug("Found user with username {}", jwtUserPrincipal.getUsername());
            UserResponse userResponse = userMapper.toUserResponse(byUsername.get());
            userResponse.setIsEmailVerified(jwtUserPrincipal.getEmailVerified());
            return userResponse;
        }

        log.debug("No user with username {}", jwtUserPrincipal.getUsername());
        User user = new User();
        user.setId(jwtUserPrincipal.getSub());
        user.setUsername(jwtUserPrincipal.getUsername());
        user.setEmail(jwtUserPrincipal.getEmail());
        user.setFirstName(jwtUserPrincipal.getFirstName());
        user.setLastName(jwtUserPrincipal.getLastName());
        UserResponse userResponse = userMapper.toUserResponse(userRepository.save(user));
        userResponse.setIsEmailVerified(jwtUserPrincipal.getEmailVerified());
        log.debug("Saved user with username {}", jwtUserPrincipal.getUsername());
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


}
