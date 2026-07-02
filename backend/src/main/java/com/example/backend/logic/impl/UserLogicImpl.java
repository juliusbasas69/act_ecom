package com.example.backend.logic.impl;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.example.backend.dao.UserDao;
import com.example.backend.dao.entity.UserEntity;
import com.example.backend.logic.UserLogic;

@Service
public class UserLogicImpl implements UserLogic{

    private final UserDao userDao;

    private final CacheManager cacheManager;

    public UserLogicImpl(UserDao userDao,
        CacheManager cacheManager
    ) {
        this.userDao = userDao;
        this.cacheManager = cacheManager;
    }

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

        System.out.println("🔥 DB CALLED");

        UserEntity user = userDao.findUserByEmail(email);

        if (cache != null && user != null) {
            cache.put(user.getEmail(), user);
        }

        return user;
    }
    
}
