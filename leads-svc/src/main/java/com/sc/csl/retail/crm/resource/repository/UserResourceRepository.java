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
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.google.common.collect.Iterables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import org.springframework.cache.annotation.Cacheable;

@JsonApiResourceRepository(User.class)
@Component
public class UserResourceRepository {
	
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(UserResourceRepository.class);
	
    private static final Map<Long, User> REPOSITORY = new ConcurrentHashMap<>();
    private static final AtomicLong ID_GENERATOR = new AtomicLong(124);

    @Autowired
    private UserRepository userRepository;
    

    @JsonApiSave
    public <S extends User> S save(S entity) {
		log.info("@save");
        return userRepository.save(entity);
    }

	//@Cacheable("userone")
    @JsonApiFindOne
    public User findOne(Long userId, QueryParams requestParams) {
		log.info("@findOne");
        if (userId == null) {
            return null;
        }         
        return userRepository.findOne(userId);
    }
	
	//@Cacheable("userall")
    @JsonApiFindAll
    public Iterable<User> findAll(QueryParams requestParams) {
		log.info("@findAll");
        return userRepository.findAll();
    }

    @JsonApiFindAllWithIds
	public Iterable<User> findAll(Iterable<Long> userIds, QueryParams requestParams) {
		log.info("@findAllWithIds");
		Iterable<User> userIterator = userRepository.findAll();
		List<User> userList = new ArrayList<User>();
		//userIterator.forEachRemaining(userList::add);
		userIterator.forEach(userList::add);
		log.info("userList size : "+userList.size());
		List<Long> idList = new ArrayList<Long>();
		userIds.forEach(idList::add);
		List<User> filterList = userList.stream().filter(u -> idList.contains(u.getId())).collect(Collectors.toList()); 
		log.info("filterList size : "+filterList.size());		
		Iterable<User> users = filterList;
		return users;
		
        //return REPOSITORY.entrySet()
        //        .stream()
        //        .filter(u -> Iterables.contains(userIds, u.getKey()))
        //        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
        //       .values();
    }

    @JsonApiDelete
    public void delete(Long userId) {
		log.info("@delete");
        userRepository.delete(userId);
    }
}
