package com.americanexpress.unify.flowret.sample;

import com.americanexpress.unify.flowret.*;

public class EndStep implements InvokableStep {
    private ProcessContext pc;

    public EndStep(ProcessContext pc) {
        this.pc = pc;
    }

    @Override
    public StepResponse executeStep() {
        System.out.println("Workflow completed.");
        return new StepResponse(UnitResponseType.OK_PROCEED, "", "");
    }
}
