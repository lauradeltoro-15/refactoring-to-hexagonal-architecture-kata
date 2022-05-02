package birthdaygreetings.test;

import birthdaygreetings.Employee;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static birthdaygreetings.test.DateHelper.*;

public class EmployeesFactory {
    public static Employee employee (String firstName, String lastName, String birthDate, String email) throws ParseException {
        return new Employee(firstName, lastName, date(birthDate), email);
    }

    public static List<Employee> employeesList(Employee ...employees) {
        return Arrays.asList(employees);
    }
}
