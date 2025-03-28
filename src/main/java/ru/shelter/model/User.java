package ru.shelter.model;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {

    private long id;

    @NotEmpty
    @NotBlank
    @Email
    private String email;

    @NotEmpty
    @NotBlank
    private String login;

    private String name;

    @PastOrPresent
    private LocalDate birthday;

    @NotNull
    private Set<Long> friends; // Инициализация коллекции

    public User() {
        friends = new HashSet<>();
    }

    public User(long id, @NotNull String email, @NotNull String login, String name, @NotNull LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
        friends = new HashSet<>();
    }
}
