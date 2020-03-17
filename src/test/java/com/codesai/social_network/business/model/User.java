package com.codesai.social_network.business.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;

public class User {
    public final String id;
    private final List<User> blockedUsers;
    public List<String> messages = new ArrayList<>();

    public User(String id) {
        this.id = id;
        this.blockedUsers = emptyList();
    }

    public User(String id, List<User> blockedUsers) {
        this.id = id;
        this.blockedUsers = blockedUsers;
    }

    public void publishMessage(String message) {
        this.messages.add(message);
    }

    public List<String> askToReadBy(User reader) {
        if (blockedUsers.stream().anyMatch(user -> user.id.equals(reader.id))) return emptyList();
        return messages;
    }
}
