package birthdaygreetings.test;

import birthdaygreetings.OurDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateHelper {
     public static OurDate date(String dateStr) throws ParseException {
        return new OurDate(new SimpleDateFormat("yyyy/MM/dd").parse(dateStr));
    }
}
