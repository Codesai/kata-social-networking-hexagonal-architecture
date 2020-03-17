import com.codesai.social_network.business.actions.PublishMessageAction;
import com.codesai.social_network.business.actions.PublishMessageCommand;
import com.codesai.social_network.business.actions.ViewMessagesAction;
import com.codesai.social_network.business.actions.ViewMessagesCommand;
import com.codesai.social_network.business.model.User;
import com.codesai.social_network.business.model.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/*
- Following:
    - Charlie can subscribe to Alice’s and Bob’s timelines, and view an aggregated list of all subscriptions
 */

public class SocialNetworkingShould {

    public static final String ALICE_ID = "aliceId";
    public static final String ANY_MESSAGE = "Welcome to our first twitch stream!";
    private static final String ALFREDO_ID = "alfredoId";
    private static final String MIGUEL_ID = "miguelId";
    private UserRepository userRepository = mock(UserRepository.class);
    private ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

    @Test
    public void
    a_user_can_publish_a_message() {
        var user = new User(ALICE_ID);
        when(userRepository.retrieveById(ALICE_ID)).thenReturn(user);

        new PublishMessageAction(userRepository).execute(new PublishMessageCommand(ALICE_ID, ANY_MESSAGE));

        verify(userRepository).save(this.captor.capture());
        assertThat(this.captor.getValue().messages).containsExactly(ANY_MESSAGE);
    }
    @Test
    public void
    a_user_can_read_the_messages_of_another_user() {
        var userWithMessages = new User(ALFREDO_ID);
        userWithMessages.publishMessage(ANY_MESSAGE);
        var reader = new User(ALICE_ID);
        when(userRepository.retrieveById(ALFREDO_ID)).thenReturn(userWithMessages);
        when(userRepository.retrieveById(ALICE_ID)).thenReturn(reader);

        var messages = new ViewMessagesAction(userRepository).execute(new ViewMessagesCommand(ALICE_ID, ALFREDO_ID));

        assertThat(messages).containsExactly(ANY_MESSAGE);
    }
    @Test
    public void
    a_user_cannot_read_the_messages_of_another_user_when_they_are_blocked() {
        var reader = new User(MIGUEL_ID);
        var userWithMessages = new User(ALFREDO_ID, singletonList(reader));

        userWithMessages.publishMessage(ANY_MESSAGE);
        when(userRepository.retrieveById(ALFREDO_ID)).thenReturn(userWithMessages);
        when(userRepository.retrieveById(MIGUEL_ID)).thenReturn(reader);

        var messages = new ViewMessagesAction(userRepository).execute(new ViewMessagesCommand(MIGUEL_ID, ALFREDO_ID));

        assertThat(messages).isEmpty();
    }
}
