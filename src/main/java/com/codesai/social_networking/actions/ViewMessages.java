package com.codesai.social_networking.actions;

import com.codesai.social_networking.services.UserRepository;

import java.util.List;
import java.util.Optional;

public class ViewMessages {
    private final UserRepository repository;

    public ViewMessages(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<List<String>> execute(ViewMessagesCommand command) {
        var reader = repository.retrieveUserById(command.reader);
        var userWithTimeline = repository.retrieveUserById(command.userName);
        return userWithTimeline.askForTimeline(reader);
    }

}
