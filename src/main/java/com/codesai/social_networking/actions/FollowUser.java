package com.codesai.social_networking.actions;

import com.codesai.social_networking.business.model.User;
import com.codesai.social_networking.services.UserRepository;

public class FollowUser {
    private final UserRepository repository;

    public FollowUser(UserRepository repository) {
        this.repository = repository;
    }

    public void execute(FollowUserCommand command) {
        var user = repository.retrieveUserById(command.follower);
        var followee = repository.retrieveUserById(command.followee);

        user.following.add(followee);

        repository.save(user);
    }
}
