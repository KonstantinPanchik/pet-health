package org.pethealth.notifications.dto.appointment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PetInfo {
    private String name;
    private String type;
    private Integer fullYears;
    private String petOwnerId;

    public void setFullYearsByYearOfBirth(Integer yearOfBirth) {
        int year = LocalDate.now().getYear();
        fullYears = year - yearOfBirth;
    }

}


