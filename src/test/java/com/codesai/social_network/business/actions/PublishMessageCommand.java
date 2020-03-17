package com.codesai.social_network.business.actions;

public class PublishMessageCommand {
    final String userId;
    final String message;

    public PublishMessageCommand(String userId, String message) {
        this.userId = userId;
        this.message = message;
    }
}
