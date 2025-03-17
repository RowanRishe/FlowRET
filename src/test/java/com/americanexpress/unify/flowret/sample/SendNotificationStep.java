package com.americanexpress.unify.flowret.sample;

import com.americanexpress.unify.flowret.InvokableStep;
import com.americanexpress.unify.flowret.ProcessContext;
import com.americanexpress.unify.flowret.ProcessVariableType;
import com.americanexpress.unify.flowret.StepResponse;
import com.americanexpress.unify.flowret.UnitResponseType;

public class SendNotificationStep implements InvokableStep {
    private ProcessContext pc;

    public SendNotificationStep(ProcessContext pc) {
        this.pc = pc;
    }

    @Override
    public StepResponse executeStep() {
        // Retrieve the LSAPrescriptions value using the public getter.
        String prescriptions = pc.getProcessVariables().getString("LSAPrescriptions");
        if (prescriptions == null) {
            prescriptions = "";
        }
        // Retrieve and increment notificationCount.
        Integer count = pc.getProcessVariables().getInteger("notificationCount");
        if (count == null) {
            count = 0;
        }
        count++;
        // Update the notificationCount using setValue().
        pc.getProcessVariables().setValue("notificationCount", ProcessVariableType.INTEGER, count);
        System.out.println("Notification " + count + ": LSA required for prescriptions: " + prescriptions);
        return new StepResponse(UnitResponseType.OK_PROCEED, "", "");
    }
}
