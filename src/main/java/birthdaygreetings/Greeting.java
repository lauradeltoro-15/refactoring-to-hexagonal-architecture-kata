package birthdaygreetings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Greeting {
    private final Employee employee;

    public static List<Greeting> composeFor(List<Employee> employees) {
        List<Greeting> greetings = new ArrayList();
        for (Employee employee: employees) {
            greetings.add(new Greeting(employee));
        }
        return greetings;
    }

    public String getEmail() {
        return employee.getEmail();
    }

    public String getTitle() {
        return "Happy Birthday!";
    }

    public String getText() {
        return "Happy Birthday, dear %NAME%!".replace("%NAME%",
                employee.getFirstName());
    }

    private Greeting(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Greeting{" +
                "employee=" + employee +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Greeting greeting = (Greeting) o;
        return Objects.equals(employee, greeting.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee);
    }
}
