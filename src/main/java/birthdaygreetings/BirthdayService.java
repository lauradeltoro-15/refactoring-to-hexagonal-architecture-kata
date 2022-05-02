package birthdaygreetings;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

public class BirthdayService {

    private final GreetingsSender greetingsSender;
    private EmployeesRepository employeesRepository;
    private Logger logger;

    public BirthdayService(EmployeesRepository employeeRepository, Logger logger) {
        employeesRepository = employeeRepository;
        this.logger = logger;
        this.greetingsSender = new EmailGreetingsSender();
    }

    public BirthdayService(EmployeesRepository employeeRepository, Logger logger, GreetingsSender greetingsSender) {
        employeesRepository = employeeRepository;
        this.logger = logger;
        this.greetingsSender = greetingsSender;
    }

    public void sendGreetings(OurDate ourDate) throws MessagingException {
        List<Employee> employees = getEmployees();
        List<Employee> employeesWhoseBirthdayIsToday = getEmployeesWhoseBirthdayIsToday(ourDate, employees);
        List<Greeting> greetings = Greeting.composeFor(employeesWhoseBirthdayIsToday);

        greetingsSender.send(greetings);
    }

    private List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();

        try {
            employees = employeesRepository.getEmployees();
        }catch (EmployeesNotFoundException e){
            logger.log("Error: employees not found");
        }catch (NotValidEmployeeException e){
            logger.log("Error: invalid employee");
        }
        return employees;
    }

    private List<Employee> getEmployeesWhoseBirthdayIsToday(OurDate ourDate, List<Employee> employees) {
        List<Employee> employeesWhoseBirthdayIsToday = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.isBirthday(ourDate)) {
                employeesWhoseBirthdayIsToday.add(employee);
            }
        }
        return employeesWhoseBirthdayIsToday;
    }

}
