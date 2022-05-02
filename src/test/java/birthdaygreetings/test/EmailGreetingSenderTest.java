package birthdaygreetings.test;

import birthdaygreetings.EmailGreetingsSender;
import birthdaygreetings.Employee;
import birthdaygreetings.Greeting;
import birthdaygreetings.NotSentGreetingsException;
import org.junit.Test;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.text.ParseException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class EmailGreetingSenderTest {

    @Test
    public void throws_an_exception_including_greetings_that_could_not_be_sent() throws ParseException, MessagingException {
        List<Employee> employees = EmployeesFactory.employeesList(
                EmployeesFactory.employee("Name", "LastName", "2008/10/08", "email@email.com"),
                EmployeesFactory.employee("Name2", "LastName2", "2008/10/08", "email2@email.com"),
                EmployeesFactory.employee("Name3", "LastName3", "2008/10/08", "email3@email.com"));
        List<Employee> employeesNotReceivedGreeting = EmployeesFactory.employeesList(
                EmployeesFactory.employee("Name", "LastName", "2008/10/08", "email@email.com"),
                EmployeesFactory.employee("Name3", "LastName3", "2008/10/08", "email3@email.com"));

        EmailGreetingsSender emailGreetingsSender = new EmailGreetingSenderWhereSendMessageFails(2);
        try {
            emailGreetingsSender.send(Greeting.composeFor(employees));
            fail("should have thrown NotSentGreetingsException");
        } catch (NotSentGreetingsException e) {
            assertThat(e.getGreetingsNotSent(), is(Greeting.composeFor(employeesNotReceivedGreeting)));
        }
    }

    class EmailGreetingSenderWhereSendMessageFails extends EmailGreetingsSender {
        private int callsToSendMessage;
        private int successfulSendMessageCall;

        public EmailGreetingSenderWhereSendMessageFails(int successfulSendMessageCall) {
            super("localhost", 25);
            this.successfulSendMessageCall = successfulSendMessageCall;
            this.callsToSendMessage = 0;

        }

        protected void sendMessage(Message msg) throws MessagingException {
            callsToSendMessage++;
            if (this.callsToSendMessage != successfulSendMessageCall) {
                throw new MessagingException();
            }
        }
    }
}
