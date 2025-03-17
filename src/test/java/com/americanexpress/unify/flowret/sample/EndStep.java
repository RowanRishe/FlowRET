package com.americanexpress.unify.flowret.sample;

import com.americanexpress.unify.flowret.InvokableStep;
import com.americanexpress.unify.flowret.ProcessContext;
import com.americanexpress.unify.flowret.StepResponse;
import com.americanexpress.unify.flowret.UnitResponseType;

public class EndStep implements InvokableStep {
    private ProcessContext pc;

    public EndStep(ProcessContext pc) {
        this.pc = pc;
    }

    @Override
    public StepResponse executeStep() {
        System.out.println("Process ended.");
        return new StepResponse(UnitResponseType.OK_PROCEED, "", "");
    }
}
