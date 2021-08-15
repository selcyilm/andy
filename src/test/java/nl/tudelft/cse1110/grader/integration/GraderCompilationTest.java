package nl.tudelft.cse1110.grader.integration;

import org.junit.jupiter.api.Test;

import java.io.File;

import static nl.tudelft.cse1110.grader.integration.GraderIntegrationTestAssertions.compilationErrorMoreTimes;
import static nl.tudelft.cse1110.grader.integration.GraderIntegrationTestHelper.justCompilation;
import static nl.tudelft.cse1110.grader.util.FileUtils.concatenateDirectories;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GraderCompilationTest extends GraderIntegrationTestBase {

    @Test
    void compilationFailure() {
        String result = run(justCompilation(), "ArrayUtilsIsSortedLibrary", "ArrayUtilsIsSortedWithCompilationError");
        assertThat(result)
                .has(GraderIntegrationTestAssertions.compilationFailure())
                .has(GraderIntegrationTestAssertions.compilationErrorOnLine(29))
                .has(GraderIntegrationTestAssertions.compilationErrorType("not a statement"))
                .has(GraderIntegrationTestAssertions.compilationErrorType("';' expected"))
                .doesNotHave(compilationErrorMoreTimes("cannot find symbol", 2));;
    }


    @Test
    void compilationSuccess() {
        String result = run(justCompilation(),  "ListUtilsLibrary", "ListUtilsCompilationSuccess");
        assertThat(result)
                .has(GraderIntegrationTestAssertions.compilationSuccess());
    }


    @Test
    void compilationDifferentFailures() {
        String result = run(justCompilation(), "MathArraysLibrary","MathArraysDifferentCompilationErrors");
        assertThat(result)
                .has(GraderIntegrationTestAssertions.compilationFailure())
                .has(GraderIntegrationTestAssertions.compilationErrorOnLine(21))
                .has(GraderIntegrationTestAssertions.compilationErrorOnLine(25))
                .has(GraderIntegrationTestAssertions.compilationErrorOnLine(33))
                .has(GraderIntegrationTestAssertions.compilationErrorMoreTimes("cannot find symbol", 3));
    }

    @Test
    void highlightsGeneratedWhenCompilationFails(){
        run(justCompilation(), "ArrayUtilsIndexOfLibrary", "ArrayUtilsIndexOfImportListCommented");

        File highlights = new File(concatenateDirectories(workDir.toString(), "highlights.json"));
        assertThat(highlights).exists().isFile();

        String expected = "{\"Error List\":[{\"Line\":40,\"Message\":\"cannot find symbol\\n  symbol:   class List\\n  location: class delft.ArrayUtilsTests\",\"Color\":\"red\"},{\"Line\":69,\"Message\":\"cannot find symbol\\n  symbol:   class List\\n  location: class delft.ArrayUtilsTests\",\"Color\":\"red\"}]}";
        assertThat(highlights).hasContent(expected);
    }
}
