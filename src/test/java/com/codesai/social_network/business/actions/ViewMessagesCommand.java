package com.codesai.social_network.business.actions;

public class ViewMessagesCommand {
    final String readerId;
    final String userWithMessagesToRead;

    public ViewMessagesCommand(String readerId, String userWithMessagesToRead) {
        this.readerId = readerId;
        this.userWithMessagesToRead = userWithMessagesToRead;
    }
}
