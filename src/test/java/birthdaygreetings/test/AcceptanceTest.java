package birthdaygreetings.test;

import birthdaygreetings.*;
import org.junit.Before;
import org.junit.Test;

import javax.mail.Message;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static birthdaygreetings.test.DateHelper.date;
import static birthdaygreetings.test.EmployeesFactory.employee;
import static birthdaygreetings.test.EmployeesFactory.employeesList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AcceptanceTest {

    private static final int SMTP_PORT = 25;
    private List<Message> messagesSent;
    private BirthdayService service;
    private EmployeesRepository employeeRepository;
    private Logger logger;


    @Before
    public void setUp() throws ParseException {
        messagesSent = new ArrayList<>();
        logger = mock(Logger.class);
        employeeRepository = mock(EmployeesRepository.class);

        when(employeeRepository.getEmployees())
                .thenReturn(
                        employeesList(
                                employee("John", "Doe", "1982/10/08", "john.doe@foobar.com"),
                                employee("Mary", "Ann", "1975/03/11", "mary.ann@foobar.com")));

        service = new BirthdayService(
                employeeRepository,
                logger,
                new EmailGreetingsSender("localhost",SMTP_PORT) {
                    @Override
                    protected void sendMessage(Message msg) {
                        messagesSent.add(msg);
                    }
                });
        }

    @Test
    public void baseScenario() throws Exception {

        service.sendGreetings(
                date("2008/10/08")
        );

        assertEquals("message not sent?", 1, messagesSent.size());
        Message message = messagesSent.get(0);
        assertEquals("Happy Birthday, dear John!", message.getContent());
        assertEquals("Happy Birthday!", message.getSubject());
        assertEquals(1, message.getAllRecipients().length);
        assertEquals("john.doe@foobar.com",
                message.getAllRecipients()[0].toString());
    }

    @Test
    public void willNotSendEmailsWhenNobodysBirthday() throws Exception {
        service.sendGreetings(
                date("2008/01/01"));

        assertEquals("what? messages?", 0, messagesSent.size());
    }

    @Test
    public void when_employees_cannot_be_found() throws Exception {
        when(employeeRepository.getEmployees()).thenThrow(new EmployeesNotFoundException("No employees found"));

        service.sendGreetings(date("2021/01/13"));

        verify(logger).log("Error: employees not found");
    }

    @Test
    public void when_employee_is_not_valid() throws Exception {
        when(employeeRepository.getEmployees()).thenThrow(new NotValidEmployeeException("Invalid employee"));

        service.sendGreetings(date("2021/01/13"));

        verify(logger).log("Error: invalid employee");
    }
}
