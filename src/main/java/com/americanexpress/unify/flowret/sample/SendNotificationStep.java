package com.americanexpress.unify.flowret.sample;

import com.americanexpress.unify.flowret.*;

public class SendNotificationStep implements InvokableStep {
    private ProcessContext pc;

    public SendNotificationStep(ProcessContext pc) {
        this.pc = pc;
    }

    @Override
    public StepResponse executeStep() {
        String prescriptions = pc.getProcessVariables().getString("SLAPrescriptions");
        if (prescriptions == null) {
            prescriptions = "";
        }
        // Retrieve and increment notificationCount
        Integer count = pc.getProcessVariables().getInteger("notificationCount");
        if (count == null) {
            count = 0;
        }
        count++;
        pc.getProcessVariables().setValue("notificationCount", ProcessVariableType.INTEGER, count);
        System.out.println("Notification " + count + ": SLA required for prescriptions: " + prescriptions);
        return new StepResponse(UnitResponseType.OK_PROCEED, "", "");
    }
}
