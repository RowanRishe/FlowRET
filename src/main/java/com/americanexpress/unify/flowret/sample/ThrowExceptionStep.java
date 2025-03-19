package com.americanexpress.unify.flowret.sample;

import com.americanexpress.unify.flowret.*;

public class ThrowExceptionStep implements InvokableStep {
    private ProcessContext pc;

    public ThrowExceptionStep(ProcessContext pc) {
        this.pc = pc;
    }

    @Override
    public StepResponse executeStep() {
        System.out.println("No response received after multiple notifications. Throwing exception.");
        return new StepResponse(UnitResponseType.ERROR_PEND, "Timeout", "");
    }
}
