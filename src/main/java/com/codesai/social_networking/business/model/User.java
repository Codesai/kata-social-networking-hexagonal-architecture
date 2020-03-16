package com.codesai.social_networking.business.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.*;

public class User {
    public final String name;
    public List<String> timeline = new ArrayList<>();
    private final List<User> blockedUsers;
    public List<User> following = new ArrayList<>();

    public User(String name) {
        this(name, emptyList());
    }

    public User(String name, List<User> blockedUsers) {
        this.name = name;
        this.blockedUsers = blockedUsers;
    }

    public void publishMessage(String message) {
        timeline.add(message);
    }

    public Optional<List<String>> askForTimeline(User reader) {
        if (hasBlocked(reader)) return Optional.empty();
        return Optional.of(timeline);
    }

    public boolean hasBlocked(User reader) {
        return blockedUsers.stream().anyMatch(user -> user.name.equals(reader.name));
    }

}
