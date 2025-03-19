package com.americanexpress.unify.flowret.sample;

import com.americanexpress.unify.flowret.*;

public class WaitResponseStep implements InvokableStep {
    private ProcessContext pc;

    public WaitResponseStep(ProcessContext pc) {
        this.pc = pc;
    }

    @Override
    public StepResponse executeStep() {
        System.out.println("Waiting for customer response...");
        // For simulation, we proceed immediately
        return new StepResponse(UnitResponseType.OK_PROCEED, "", "");
    }
}
