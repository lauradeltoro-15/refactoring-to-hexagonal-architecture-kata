package birthdaygreetings;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

public class EmailGreetingsSender implements GreetingsSender {

    private String smtpHost;
    private int smtpPort;
    List<Greeting> notSentGreetings;

    public EmailGreetingsSender() {
        this("localhost", 25);
    }

    public EmailGreetingsSender(String smtpHost, int smtpPort) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
    }

    @Override
    public void send(List<Greeting> greetings) throws MessagingException {
        notSentGreetings = new ArrayList<>();
        for (Greeting greeting : greetings) {
            sendGreeting(greeting);
        }
        if (!notSentGreetings.isEmpty()){
            throw new NotSentGreetingsException(notSentGreetings);
        }
    }

    private void sendGreeting(Greeting greeting) throws MessagingException {
        Session session = createMailSession();
        Message msg = createMessage(greeting, session);
        try {
            sendMessage(msg);
        } catch (Exception e) {
            notSentGreetings.add(greeting);
        }
    }
    // made protected for testing :-(
    protected Message createMessage(Greeting greeting, Session session) throws MessagingException {
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("sender@here.com"));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(
                greeting.getEmail()));
        msg.setSubject(greeting.getTitle());
        msg.setText(greeting.getText());
        return msg;
    }

    // made protected for testing :-(
    protected void sendMessage(Message msg) throws MessagingException {
        Transport.send(msg);
    }

    private Session createMailSession() {
        java.util.Properties props = new java.util.Properties();
        props.put("mail.smtp.host", this.smtpHost);
        props.put("mail.smtp.port", "" + this.smtpPort);
        return Session.getDefaultInstance(props, null);
    }

}
