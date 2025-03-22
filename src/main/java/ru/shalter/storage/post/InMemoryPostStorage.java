package ru.shalter.storage.post;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import ru.shalter.enums.SortOrder;
import ru.shalter.exeption.ConditionsNotMetException;
import ru.shalter.model.Post;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryPostStorage implements PostStorage{

    private final Map<Long, Post> posts = new HashMap<>();

    public Set<Long> getAllPostsId() {
        return posts.keySet();
    }

    public Collection<Post> getAllPosts() {
        return posts.values();
    }

    public Collection<Post> findAll(int from, int size, SortOrder sortOrder) {
        return posts.values().stream()
                .sorted((p1, p2) -> {
                    if (sortOrder == SortOrder.ASCENDING) {
                        return p1.getPostDate().compareTo(p2.getPostDate());
                    } else {
                        return p2.getPostDate().compareTo(p1.getPostDate());
                    }
                })
                .skip(from)
                .limit(size)
                .collect(Collectors.toList());
    }

    public Post create(Post post) {
        post.setId(getNextId());
        post.setPostDate(Instant.now());
        posts.put(post.getId(), post);
        return post;
    }

    public Post update(Post newPost) {
            Post oldPost = posts.get(newPost.getId());
            if (newPost.getDescription() == null || newPost.getDescription().isBlank()) {
                throw new ConditionsNotMetException("Описание не может быть пустым");
            }
            oldPost.setDescription(newPost.getDescription());
            return oldPost;
    }

    public Optional<Post> findById(@PathVariable long postId) {
        return posts.values().stream()
                .filter(x -> x.getId() == postId)
                .findFirst();
    }

    private long getNextId() {
        long currentMaxId = posts.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
