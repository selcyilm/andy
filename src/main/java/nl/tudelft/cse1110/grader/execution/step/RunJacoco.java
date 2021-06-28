package nl.tudelft.cse1110.grader.execution.step;

import nl.tudelft.cse1110.grader.config.Configuration;
import nl.tudelft.cse1110.grader.execution.ExecutionFlow;
import nl.tudelft.cse1110.grader.execution.ExecutionStep;
import nl.tudelft.cse1110.grader.result.ResultBuilder;

public class RunJacoco implements ExecutionStep {
    @Override
    public void execute(Configuration cfg, ExecutionFlow flow, ResultBuilder result) {
        flow.next(new GenerateResultsStep());
    }
}
