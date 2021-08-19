package nl.tudelft.cse1110;

import org.assertj.core.api.Condition;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ResultTestAssertions {

    private static Condition<String> containsRegex(String regex) {
        return new Condition<>() {
            @Override
            public boolean matches(String value) {
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(value);
                return matcher.find();
            }
        };
    }

    private static Condition<String> containsString(String regex) {
        return new Condition<>() {
            @Override
            public boolean matches(String value) {
                return value.contains(regex);
            }
        };
    }

    public static Condition<String> compilationErrorMoreTimes(String errorType, int times) {
        return new Condition<>() {
            @Override
            public boolean matches(String value) {
                String regex = "- line \\d+: " + errorType;
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(value);
                int count = 0;
                while (matcher.find()) {
                    count++;
                }

                return count == times;
            }
        };
    }

    public static Condition<String> numberOfJUnitTestsPassing(int numberOfTestsPassing) {
        return containsRegex("--- JUnit execution\\n" + numberOfTestsPassing + "\\/\\d+ passed");
    }


    public static Condition<String> totalNumberOfJUnitTests(int numberOfTests) {
        return containsRegex("--- JUnit execution\\n\\d+\\/" + numberOfTests + " passed");
    }


    public static Condition<String> propertyTestFailing(String testName) {
        return containsRegex("- Property test \"" + testName + "\" failed:\n\\{.*" + testName);
    }


    public static Condition<String> parameterizedTestFailing(String testName, int testCaseNumber) {
        return containsString("- Parameterized test \"" + testName + "\", test case #" + testCaseNumber + " failed:");
    }


    public static Condition<String> compilationFailure() {
        return containsRegex("We could not compile your code\\. See the compilation errors below:");
    }


    public static Condition<String> compilationSuccess() {
        return containsString("--- Compilation\nSuccess");
    }


    public static Condition<String> compilationErrorOnLine(int lineNumber) {
        return containsRegex("- line " + lineNumber + ":\\s");
    }


    public static Condition<String> compilationErrorType(String errorType) {
        return containsRegex("- line \\d+: " + errorType);
    }



    public static Condition<String> failingTestName(String testName) {
        return containsRegex("- Test " + "\"" + testName + "\\(" + "\\)" + "\"" + " failed:");
    }


    public static Condition<String> errorType(String errorType) {
        return containsRegex("\\w." + errorType);
    }


    public static Condition<String> errorMessage(String errorMessage) {
        return containsString(errorMessage);
    }


    public static Condition<String> failingParameterizedTestName(String testName) {
        return containsString("- Parameterized test " + "\"" + testName + "\"" + ",");
    }


    public static Condition<String> parameterizedTestCaseNumber(int testCaseNumber) {
        return containsString(" test case #" + testCaseNumber + " failed:");
    }


    public static Condition<String> uninvokedMethod(String uninvokedMethod) {
        return containsString("Wanted but not invoked:\n" + uninvokedMethod);
    }


    public static Condition<String> hintAtInteractionFound(String invokedMethod) {
        return containsString("However, there was exactly 1 interaction with this mock:\n" + invokedMethod);
    }

    public static Condition<String> linesCovered(int numberOfLinesCovered) {
        return containsRegex("Line coverage: " + numberOfLinesCovered + "/\\d+");
    }

    public static Condition<String> instructionsCovered(int numberOfInstructionsCovered) {
        return containsRegex("Instruction coverage: " + numberOfInstructionsCovered + "/\\d+");
    }

    public static Condition<String> branchesCovered(int numberOfBranchesCovered) {
        return containsRegex("Branch coverage: " + numberOfBranchesCovered + "/\\d+");
    }

    public static Condition<String> metaTestsPassing(int numberOfMetaTestsPassing) {
        return containsRegex("--- Meta tests\n" + numberOfMetaTestsPassing + "/\\d+ passed");
    }

    public static Condition<String> metaTests(int numberOfMetaTests) {
        return containsRegex("--- Meta tests\n\\d+/" + numberOfMetaTests + " passed");
    }

    public static Condition<String> metaTestFailing(String metaTestName) {
        return containsString("Meta test: " + metaTestName + " FAILED");
    }

    public static Condition<String> finalGrade(int score) {
        return containsString("--- Final grade\n" + score + "/100");
    }

    public static Condition<String> mutationScore(int mutantsKilled, int totalMutants) {
        return containsString("--- Mutation testing\n" + mutantsKilled + "/" + totalMutants);
    }

    public static Condition<String> scoreOfCodeChecks(int points, int total) {
        return containsString("Code checks score: " + points + "/" + total);
    }

    public static Condition<String> codeCheck(String description, boolean pass, int weight) {
        String expectedCheck = String.format("%s: %s (weight: %d)",
                description,
                pass ? "PASS" : "FAIL",
                weight);

        return containsString(expectedCheck);
    }

    public static Condition<String> codeCheckScores() {
        return containsRegex("Code checks score: \\d*/\\d*");
    }

    public static Condition<String> totalTimeToExecute() {
        return containsRegex("Our grader took \\d+.\\d seconds to assess your question.");
    }

}
