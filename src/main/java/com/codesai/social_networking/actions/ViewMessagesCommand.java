package com.codesai.social_networking.actions;

public class ViewMessagesCommand {
    public final String reader;
    public final String userName;

    public ViewMessagesCommand(String reader, String userName) {
        this.reader = reader;
        this.userName = userName;
    }
}
