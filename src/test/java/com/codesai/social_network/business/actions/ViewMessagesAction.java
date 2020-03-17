package com.codesai.social_network.business.actions;

import com.codesai.social_network.business.model.UserRepository;

import java.util.List;

public class ViewMessagesAction {
    private final UserRepository repository;

    public ViewMessagesAction(UserRepository repository) {
        this.repository = repository;
    }

    public List<String> execute(ViewMessagesCommand command) {
        var userWithMessages = repository.retrieveById(command.userWithMessagesToRead);
        var reader = repository.retrieveById(command.readerId);

        return userWithMessages.askToReadBy(reader);
    }
}
