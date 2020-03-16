package com.codesai.social_networking.actions;

public class PublishMessageCommand {
    public final String name;
    public final String message;

    public PublishMessageCommand(String name, String message) {
        this.name = name;
        this.message = message;
    }
}
