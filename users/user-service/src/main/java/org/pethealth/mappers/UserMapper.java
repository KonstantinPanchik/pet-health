package org.pethealth.mappers;

import org.pethealth.dto.response.UserResponse;
import org.pethealth.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .city(user.getCity())
                .address(user.getAddress())
                .email(user.getEmail())
                .phone(user.getPhone())
                .sex(user.getSex())
                .birthday(user.getBirthday())
                .build();
    }

}
