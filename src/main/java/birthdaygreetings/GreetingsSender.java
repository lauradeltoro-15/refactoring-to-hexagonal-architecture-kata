package birthdaygreetings;

import javax.mail.MessagingException;
import java.util.List;

public interface GreetingsSender {
    void send(List<Greeting> greetings) throws MessagingException;
}
