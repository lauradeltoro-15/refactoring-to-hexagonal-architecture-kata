package birthdaygreetings;

import java.util.List;

public class NotSentGreetingsException extends RuntimeException{
    private List<Greeting> notSentGreetings;

    public NotSentGreetingsException(List<Greeting> notSentGreetings) {
        this.notSentGreetings = notSentGreetings;
    }

    public List<Greeting> getGreetingsNotSent() {
        return notSentGreetings;
    }
}
