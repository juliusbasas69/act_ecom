package com.example.backend.logic.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.example.backend.dao.UserDao;
import com.example.backend.dao.entity.UserEntity;
import com.example.backend.logic.UserLogic;

@Service
public class UserLogicImpl implements UserLogic{

    @Value("${user.max.display}")
    private String USER_MAX_DISPLAY;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CacheManager cacheManager;

    @Override
    public void saveUser(UserEntity user) {
        
        userDao.save(user);
    }

    @Override
    public UserEntity findUserByEmail(String email) {

        Cache cache = cacheManager.getCache("users");

        if (cache != null) {
            UserEntity cached = cache.get(email, UserEntity.class);
            if (cached != null) {
                return cached;
            }
        }

        System.out.println("DB CALLED");

        UserEntity user = userDao.findUserByEmail(email);

        if (cache != null && user != null) {
            cache.put(user.getEmail(), user);
        }

        return user;
    }

    @Override
    public Page<UserEntity> getAllUsers(int page, String search) {

        Pageable pageable = PageRequest.of(page, Integer.parseInt(USER_MAX_DISPLAY));
        
        return userDao.getAllUsers(pageable, search);
    }

    @Override
    public Optional<UserEntity> findUserById(int id) {
        
        return userDao.findById(id);
    }
    
}
