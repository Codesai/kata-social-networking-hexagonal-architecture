package com.codesai.social_networking.services;

import com.codesai.social_networking.business.model.User;

public interface UserRepository {
    void save(User user);

    User retrieveUserById(String name);
}
