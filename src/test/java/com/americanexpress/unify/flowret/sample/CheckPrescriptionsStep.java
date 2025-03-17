package com.americanexpress.unify.flowret.sample;

import com.americanexpress.unify.flowret.InvokableStep;
import com.americanexpress.unify.flowret.ProcessContext;
import com.americanexpress.unify.flowret.ProcessVariableType;
import com.americanexpress.unify.flowret.StepResponse;
import com.americanexpress.unify.flowret.UnitResponseType;

public class CheckPrescriptionsStep implements InvokableStep {
    private ProcessContext pc;

    public CheckPrescriptionsStep(ProcessContext pc) {
        this.pc = pc;
    }

    @Override
    public StepResponse executeStep() {
        // Simulate checking a database for prescriptions requiring LSA.
        String prescriptions = "Rx101, Rx205";  // Example data.
        // Set the process variable using the public setValue method.
        pc.getProcessVariables().setValue("LSAPrescriptions", ProcessVariableType.STRING, prescriptions);
        System.out.println("Checked prescriptions; found LSA-required ones: " + prescriptions);
        return new StepResponse(UnitResponseType.OK_PROCEED, "", "");
    }
}
