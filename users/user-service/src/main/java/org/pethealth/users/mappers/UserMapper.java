package org.pethealth.users.mappers;

import org.pethealth.notifications.dto.person.UserEvent;
import org.pethealth.users.dto.response.UserResponse;
import org.pethealth.users.entities.User;
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

    public UserEvent toUserEvent(User user) {
        UserEvent userEvent = new UserEvent();
        userEvent.setId(user.getId());
        userEvent.setFirstName(user.getFirstName());
        userEvent.setLastName(user.getLastName());
        userEvent.setEmail(user.getEmail());
        return userEvent;
    }

}
