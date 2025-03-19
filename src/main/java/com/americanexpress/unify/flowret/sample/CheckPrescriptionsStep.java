package com.americanexpress.unify.flowret.sample;

import com.americanexpress.unify.flowret.*;

public class CheckPrescriptionsStep implements InvokableStep {
    private ProcessContext pc;

    public CheckPrescriptionsStep(ProcessContext pc) {
        this.pc = pc;
    }

    @Override
    public StepResponse executeStep() {
        // Simulate checking the database for prescriptions for a given customer.
        String customerName = pc.getProcessVariables().getString("customerName");
        String prescriptions;
        if ("Alice".equalsIgnoreCase(customerName)) {
            // Dummy data: prescriptions that require an SLA.
            prescriptions = "Rx101, Rx205";
        } else {
            prescriptions = "";
        }
        // Set the correct naming convention: "SLAPrescriptions"
        pc.getProcessVariables().setValue("SLAPrescriptions", ProcessVariableType.STRING, prescriptions);
        System.out.println("Checked prescriptions; found SLA-required ones: " + prescriptions);
        return new StepResponse(UnitResponseType.OK_PROCEED, "", "");
    }
}
