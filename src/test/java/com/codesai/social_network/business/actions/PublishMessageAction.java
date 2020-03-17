package com.codesai.social_network.business.actions;

import com.codesai.social_network.business.model.UserRepository;

public class PublishMessageAction {
    private final UserRepository repository;

    public PublishMessageAction(UserRepository repository) {
        this.repository = repository;
    }

    public void execute(PublishMessageCommand command) {
        var user = this.repository.retrieveById(command.userId);
        user.publishMessage(command.message);
        this.repository.save(user);
    }
}
