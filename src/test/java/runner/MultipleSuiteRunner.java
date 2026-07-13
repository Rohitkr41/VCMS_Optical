package runner;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

public class MultipleSuiteRunner {

    public static void main(String[] args) {

        int runCount = 6;

        for (int i = 1; i <= runCount; i++) {

            System.out.println("================================");
            System.out.println("RUN NUMBER : " + i);
            System.out.println("================================");

            TestNG testng = new TestNG();

            List<String> suites = new ArrayList<>();

            suites.add("testng.xml");

            testng.setTestSuites(suites);

            testng.run();
        }
    }
}
