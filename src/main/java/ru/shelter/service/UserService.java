package ru.shelter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shelter.exeption.DuplicatedDataException;
import ru.shelter.exeption.NotFoundException;
import ru.shelter.exeption.ValidationException;
import ru.shelter.model.User;
import ru.shelter.storage.user.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.Collection;

@Service
public class UserService {

    private final InMemoryUserStorage inMemoryUserStorage;

    @Autowired
    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public Collection<User> findAll() {
        if (inMemoryUserStorage.findAll().isEmpty()) {
            throw new NotFoundException("Нет активных пользователей");
        }
        return inMemoryUserStorage.findAll();
    }

    public User get(Long id) {
        errorOfUserExist(id);
        return inMemoryUserStorage.getUser(id);
    }

    public Collection<Long> findAllFriends(Long id) {
        errorOfUserExist(id);
        return inMemoryUserStorage.findAllFriends(id);
    }

    public Collection<Long> getCommonFriends(Long id, Long friendId) {
        errorOfUserExist(id);
        errorOfUserExist(friendId);
        return inMemoryUserStorage.getCommonFriends(id, friendId);
    }

    public User create(User user) {
        validateUser(user);
        if (inMemoryUserStorage.findAll().stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(user.getEmail()))) {
            throw new DuplicatedDataException("Этот имейл уже используется");
        }
        inMemoryUserStorage.create(user);
        return user;
    }

    public User update(User newUser) {
        errorOfUserExist(newUser.getId());
        validateUser(newUser);
        return inMemoryUserStorage.update(newUser);
    }

    public long addFriend(Long id, Long friendId) {
        errorOfUserExist(id);
        errorOfUserExist(friendId);
        return inMemoryUserStorage.addFriend(id, friendId);
    }

    public boolean deleteFriend(Long id, Long friendId) {
        errorOfUserExist(id);
        errorOfUserExist(friendId);
        return inMemoryUserStorage.deleteFriend(id, friendId);
    }

    private void validateUser(User user) {
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new ValidationException("Некорректный логин пользователя");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            throw new ValidationException("Некорректный имейл пользователя");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday() != null && user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Некорректная дата рождения пользователя");
        }
    }

    private void errorOfUserExist(Long id) {
        if (!inMemoryUserStorage.findAllKeys().contains(id)) {
            throw new NotFoundException("Пользователь с id = " + id + " не найден");
        }
    }
}
