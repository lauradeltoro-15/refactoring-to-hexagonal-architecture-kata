package birthdaygreetings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FileEmployeesRepository implements EmployeesRepository {
    private String fileName;

    public FileEmployeesRepository(String fileName) {
        this.fileName = fileName;
    }

    public List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(this.fileName));
            String line = "";
            line = in.readLine(); // skip header
            while ((line = in.readLine()) != null) {
                Employee employee = EmployeeRecord.from(line).toEmployee();
                employees.add(employee);
            }
        } catch (IOException e)  {
            throw new EmployeesNotFoundException(e.getMessage());
        }

        return employees;
    }

    static class EmployeeRecord {

        private final String[] employeeData;

        public EmployeeRecord (String line) {
            employeeData = line.split(", ");
        }

        private static EmployeeRecord from(String line) {
            return new EmployeeRecord(line);
        }

        private OurDate birthDate()  {
            try {
                return new OurDate(new SimpleDateFormat("yyyy/MM/dd").parse(employeeData[2]));
            } catch (ParseException e) {
                throw new NotValidEmployeeException(e.getMessage());
            }
        }
        private String firstName() {
            return employeeData[1];
        }
        private String lastName() {
            return employeeData[0];
        }
        private String email() {
            return employeeData[3];
        }

        public Employee toEmployee() {
            return new Employee(firstName(), lastName(), birthDate(), email());
        }
    }
}
