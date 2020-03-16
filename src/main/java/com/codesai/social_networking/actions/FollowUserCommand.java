package com.codesai.social_networking.actions;

public class FollowUserCommand {
    public final String follower;
    public final String followee;

    public FollowUserCommand(String follower, String followee) {
        this.follower = follower;
        this.followee = followee;
    }
}
