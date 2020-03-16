import com.codesai.social_networking.actions.*;
import com.codesai.social_networking.business.model.User;
import com.codesai.social_networking.services.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class SocialNetworkingShould {

    public static final String ANY_MESSAGE = "AnyMessage";
    public static final String ANY_USER_NAME = "ANY_USER_NAME";
    private final ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
    private UserRepository repository = mock(UserRepository.class);

    @Test
    public void publish_messages_to_a_personal_timeline() {
        var user = new User("Alice");
        when(this.repository.retrieveUserById(user.name)).thenReturn(user);

        new PublishMessage(this.repository).execute(new PublishMessageCommand(user.name, aMessageFrom(user.name)));

        verify(this.repository).save(captor.capture());
        assertThat(captor.getValue().askForTimeline(new User(user.name))).contains(singletonList(aMessageFrom(user.name)));
    }

    @Test
    public void a_user_can_view_another_user_published_messages() {
        var aUserWithTimeline = new User("Alice");
        aUserWithTimeline.publishMessage(aMessageFrom(aUserWithTimeline.name));
        when(this.repository.retrieveUserById(aUserWithTimeline.name)).thenReturn(aUserWithTimeline);
        when(this.repository.retrieveUserById(ANY_USER_NAME)).thenReturn(new User(ANY_USER_NAME));

        var userTimeline = new ViewMessages(this.repository).execute(new ViewMessagesCommand(ANY_USER_NAME, aUserWithTimeline.name));

        assertThat(userTimeline).contains(singletonList(aMessageFrom(aUserWithTimeline.name)));
    }

    @Test
    public void a_blocked_user_cannot_see_the_timeline_of_the_user_that_blocked_they() {
        var blockedUser = new User("Robert");
        var aUserWithTimeline = new User("Alice", singletonList(blockedUser));
        aUserWithTimeline.publishMessage(aMessageFrom(aUserWithTimeline.name));
        when(this.repository.retrieveUserById(aUserWithTimeline.name)).thenReturn(aUserWithTimeline);
        when(this.repository.retrieveUserById(blockedUser.name)).thenReturn(blockedUser);

        var userTimeline = new ViewMessages(this.repository).execute(new ViewMessagesCommand(blockedUser.name, aUserWithTimeline.name));

        assertThat(userTimeline).isEmpty();
    }

    @Test
    public void a_user_can_follow_another_user() {
        var follower = new User("Robert");
        var followee = new User("Alice");
        followee.publishMessage(aMessageFrom(follower.name));
        when(this.repository.retrieveUserById(followee.name)).thenReturn(followee);
        when(this.repository.retrieveUserById(follower.name)).thenReturn(follower);

        new FollowUser(this.repository).execute(new FollowUserCommand(follower.name, followee.name));

        verify(repository).save(captor.capture());
        assertThat(captor.getValue().following).containsExactly(followee);
    }

    @Test public void
    a_user_can_view_an_aggregated_of_the_timeline_messages_from_followed_users() {
        var alice = new User("Alice");
        alice.publishMessage(aMessageFrom(alice.name));
        var bob = new User("Bob");
        bob.publishMessage(aMessageFrom(bob.name));
        var charlie = new User("Charlie");

        charlie.following.add(alice);
        charlie.following.add(bob);

        when(repository.retrieveUserById(charlie.name)).thenReturn(charlie);

        var timeline = new ViewTimeline(repository).execute(new ViewTimelineCommand(charlie.name));

        assertThat(timeline).containsExactlyInAnyOrder(aMessageFrom(alice.name), aMessageFrom(bob.name));
    }

    private String aMessageFrom(String name) {
        return ANY_MESSAGE + " by " + name;
    }
}
