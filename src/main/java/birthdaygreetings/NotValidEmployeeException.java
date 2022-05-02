package birthdaygreetings;

public class NotValidEmployeeException extends RuntimeException {
    public NotValidEmployeeException(String message) {
        super(message);
    }
}
