package com.sc.csl.retail.crm.resource.repository;

import com.sc.csl.retail.crm.persistence.dao.UserRepository;
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

//import org.springframework.cache.annotation.Cacheable;

@JsonApiResourceRepository(User.class)
@Component
public class UserResourceRepository {
    private static final Map<Long, User> REPOSITORY = new ConcurrentHashMap<>();
    private static final AtomicLong ID_GENERATOR = new AtomicLong(124);

    @Autowired
    private UserRepository userRepository;
    

    @JsonApiSave
    public <S extends User> S save(S entity) {
        return userRepository.save(entity);
    }

	//@Cacheable("userone")
    @JsonApiFindOne
    public User findOne(Long userId, QueryParams requestParams) {
        if (userId == null) {
            return null;
        }
          
        return userRepository.findOne(userId);
    }
	
	//@Cacheable("userall")
    @JsonApiFindAll
    public Iterable<User> findAll(QueryParams requestParams) {
        return userRepository.findAll();
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
