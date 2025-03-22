package ru.shelter.storage.post;

import ru.shelter.enums.SortOrder;
import ru.shelter.model.Post;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface PostStorage {

    Set<Long> getAllPostsId();

    Collection<Post> getAllPosts();

    Collection<Post> findAll(int from, int size, SortOrder sortOrder);

    Post create(Post post);

    Post update(Post newPost);

    Optional<Post> findById(long postId);
}
