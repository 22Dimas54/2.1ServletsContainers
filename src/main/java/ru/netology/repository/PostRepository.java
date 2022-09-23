package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

// Stub
public class PostRepository {
    private ConcurrentHashMap<Integer, Post> concurrentHashMap;
    private long idPost;

    public PostRepository() {
        concurrentHashMap = new ConcurrentHashMap<>();
    }

    public List<Post> all() {
        List<Post> postList =
                concurrentHashMap.entrySet()
                        .stream()
                        .map(e -> e.getValue())
                        .collect(Collectors.toList());
        return postList;
    }

    public Optional<Post> getById(long id) {
        if (concurrentHashMap.containsKey(Math.toIntExact(id))) {
            var foundPost = concurrentHashMap.get(Math.toIntExact(id));
            return Optional.of(foundPost);
        } else {
            return Optional.empty();
        }
    }

    public Post save(Post post) {
        if (post.getId() < 0) {
            throw new NotFoundException("Not found");
        } else if (post.getId() == 0) {
            idPost++;
            post.setId(idPost);
            concurrentHashMap.put(Math.toIntExact(post.getId()), post);
        } else {
            var foundPost = concurrentHashMap.get(Math.toIntExact(post.getId()));
            if (foundPost != null) {
                concurrentHashMap.put(Math.toIntExact(post.getId()), post);
            } else {
                throw new NotFoundException("Not found");
            }
        }
        return post;
    }

    public void removeById(long id) throws NotFoundException {
        if (concurrentHashMap.containsKey(Math.toIntExact(id))) {
            concurrentHashMap.remove(Math.toIntExact(id));
        } else {
            throw new NotFoundException("Not found");
        }
    }
}
