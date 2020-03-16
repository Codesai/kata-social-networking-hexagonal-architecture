package com.codesai.social_networking.actions;

import com.codesai.social_networking.services.UserRepository;

import java.util.Collection;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class ViewTimeline {
    private final UserRepository repository;

    public ViewTimeline(UserRepository repository) {
        this.repository = repository;
    }

    public List<String> execute(ViewTimelineCommand command) {
        var user = repository.retrieveUserById(command.name);
        return user.following.stream()
                .map(followedUser -> followedUser.askForTimeline(user).orElse(emptyList()))
                .flatMap(Collection::stream)
                .collect(toList());
    }
}
