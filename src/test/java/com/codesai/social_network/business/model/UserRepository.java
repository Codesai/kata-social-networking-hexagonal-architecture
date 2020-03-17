package com.codesai.social_network.business.model;

public interface UserRepository {
    void save(User user);

    User retrieveById(String id);
}
