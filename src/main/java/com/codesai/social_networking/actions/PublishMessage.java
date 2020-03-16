package com.codesai.social_networking.actions;

import com.codesai.social_networking.services.UserRepository;

public class PublishMessage {
    private final UserRepository repository;

    public PublishMessage(UserRepository repository) {
        this.repository = repository;
    }

    public void execute(PublishMessageCommand command) {
        var user = repository.retrieveUserById(command.name);
        user.publishMessage(command.message);
        repository.save(user);
    }

}
