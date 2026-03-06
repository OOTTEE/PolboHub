package com.polbohub.domain.user;

import java.time.LocalDate;
import java.time.Period;


public record User(
        UserId id,
        String username,
        String firstName,
        String lastName,
        LocalDate birthDate
) {

    public int getAge() {
        if (this.birthDate() == null) return 0;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public enum Role {
        USER, STAFF, ADMIN
    }
}
