package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import com.github.javafaker.Faker;

public class TestDataGenerator {
	  static Faker faker = new Faker(new Locale("en", "IN"));

    public static String getFirstName() {
        return faker.name().firstName();
    }

    public static String getLastName() {
        return faker.name().lastName();
    }
    public static String getAgeYear() {
        return String.valueOf(faker.number().numberBetween(18, 70));
    }

    public static String getAgeMonth() {
        return String.valueOf(faker.number().numberBetween(0, 11));
    }

    public static String getNextOfKin() {
        return faker.name().fullName();
    }

//    public static String getMobileNumber() {
//        return "9" + faker.number().digits(9);
//    }
    
    public static String getMobileNumber() {
        String uniqueDigits = String.valueOf(System.currentTimeMillis());
        uniqueDigits = uniqueDigits.substring(uniqueDigits.length() - 9);

        return "7" + uniqueDigits;
    }

    public static String getDrivingLicense() {
        return "DL" + faker.number().digits(10);
    }

    public static String getAddress() {
        return faker.address().streetAddress();
    }

    public static String getPastDate() {
        LocalDate today = LocalDate.now();

        // 1 to 3650 days back, so today/future date kabhi nahi aayegi
        long randomDaysBack = ThreadLocalRandom.current().nextLong(1, 3651);

        LocalDate pastDate = today.minusDays(randomDaysBack);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return pastDate.format(formatter);
    }
}