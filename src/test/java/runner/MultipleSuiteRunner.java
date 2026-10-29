
package runner;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

public class MultipleSuiteRunner {

    public static void main(String[] args) {

        int runCount = 2;

        String suitePath = "src/test/resources/clinicalExamination.xml";

        for (int i = 1; i <= runCount; i++) {

            System.out.println("\n");
            System.out.println("======================================");
            System.out.println(" CLINICAL SUITE RUN NUMBER : " + i);
            System.out.println("======================================");

            TestNG testng = new TestNG();

            List<String> suiteFiles = new ArrayList<>();
            suiteFiles.add(suitePath);

            testng.setTestSuites(suiteFiles);

            // Har iteration ki report alag folder mein save hogi
            testng.setOutputDirectory("test-output/run-" + i);

            // Suite ko sequentially execute karega
            testng.setParallel(org.testng.xml.XmlSuite.ParallelMode.NONE);

            testng.run();

            System.out.println("\n======================================");
            System.out.println(" SUITE RUN COMPLETED : " + i);
            System.out.println("======================================");
        }

        System.out.println("\nAll 4 suite iterations have completed.");
    }
}
