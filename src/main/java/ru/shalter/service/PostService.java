package ru.shalter.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.shalter.enums.SortOrder;
import ru.shalter.exeption.ConditionsNotMetException;
import ru.shalter.exeption.NotFoundException;
import ru.shalter.model.Post;
import ru.shalter.storage.post.InMemoryPostStorage;

import java.util.Collection;
import java.util.Optional;

// Указываем, что класс PostService - является бином и его
// нужно добавить в контекст приложения
@Service
public class PostService {
    private final InMemoryPostStorage inMemoryPostStorage = new InMemoryPostStorage();

    public Collection<Post> findAll(int from, int size, SortOrder sortOrder) {
        return inMemoryPostStorage.findAll(from, size, sortOrder);
    }

    public Post create(Post post) {
        if (post.getDescription() == null || post.getDescription().isBlank()) {
            throw new ConditionsNotMetException("Описание не может быть пустым");
        }
        return inMemoryPostStorage.create(post);
    }

    public Post update(Post newPost) {
        if (newPost.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }
        if (inMemoryPostStorage.getAllPostsId().contains(newPost.getId())) {
            return inMemoryPostStorage.update(newPost);
        }
        throw new NotFoundException("Пост с id = " + newPost.getId() + " не найден");
    }

    public Optional<Post> findById(long postId) {
        return inMemoryPostStorage.findById(postId);
    }
}
