package com.americanexpress.unify.flowret.sample;

import com.americanexpress.unify.flowret.InvokableStep;
import com.americanexpress.unify.flowret.ProcessContext;
import com.americanexpress.unify.flowret.StepResponse;
import com.americanexpress.unify.flowret.UnitResponseType;

public class WaitResponseStep implements InvokableStep {
    private ProcessContext pc;

    public WaitResponseStep(ProcessContext pc) {
        this.pc = pc;
    }

    @Override
    public StepResponse executeStep() {
        System.out.println("Waiting for customer response...");
        // In a real scenario, this might poll or wait for an external event.
        return new StepResponse(UnitResponseType.OK_PROCEED, "", "");
    }
}
