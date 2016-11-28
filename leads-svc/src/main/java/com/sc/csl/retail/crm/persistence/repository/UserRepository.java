package com.sc.csl.retail.crm.persistence.entity.repository;

import com.sc.csl.retail.crm.persistence.entity.User;
import io.katharsis.queryParams.QueryParams;
import io.katharsis.repository.annotations.JsonApiDelete;
import io.katharsis.repository.annotations.JsonApiFindAll;
import io.katharsis.repository.annotations.JsonApiFindAllWithIds;
import io.katharsis.repository.annotations.JsonApiFindOne;
import io.katharsis.repository.annotations.JsonApiResourceRepository;
import io.katharsis.repository.annotations.JsonApiSave;
import io.katharsis.resource.exception.ResourceNotFoundException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.google.common.collect.Iterables;

@JsonApiResourceRepository(User.class)
@Component
public class UserRepository {
    private static final Map<Long, User> REPOSITORY = new ConcurrentHashMap<>();
    private static final AtomicLong ID_GENERATOR = new AtomicLong(124);

    @Autowired @Lazy
    public UserRepository() {
        User user = new User();
        user.setEmployeeName("Smith");
        user.setPwId("123");
        user.setRole("Lead");
        save(user);
    }

    @JsonApiSave
    public <S extends User> S save(S entity) {
//        if (entity.getId() == null) {
//            entity.setId(ID_GENERATOR.getAndIncrement());
//        }
//        REPOSITORY.put(entity.getId(), entity);
        return entity;
    }

    @JsonApiFindOne
    public User findOne(Long userId, QueryParams requestParams) {
        if (userId == null) {
            return null;
        }
        User user = REPOSITORY.get(userId);
        if (user == null) {
            throw new ResourceNotFoundException("User not found!");
        }
        
/*        if (project.getTasks().isEmpty()) {
            Iterable<Task> tasks = taskRepository.findAll(null);
            tasks.forEach(task -> {
                if (task.getProjectId().equals(project.getId())) {
                    project.getTasks().add(task);
                }
            });
            save(project);
        }
*/        
        return user;
    }

    @JsonApiFindAll
    public Iterable<User> findAll(QueryParams requestParams) {
        return REPOSITORY.values();
    }

    @JsonApiFindAllWithIds
    public Iterable<User> findAll(Iterable<Long> userIds, QueryParams requestParams) {
        return REPOSITORY.entrySet()
                .stream()
                .filter(u -> Iterables.contains(userIds, u.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                .values();
    }

    @JsonApiDelete
    public void delete(Long userId) {
        REPOSITORY.remove(userId);
    }
}
