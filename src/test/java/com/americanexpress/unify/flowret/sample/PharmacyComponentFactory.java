package com.americanexpress.unify.flowret.sample;

import com.americanexpress.unify.flowret.ProcessComponentFactory;
import com.americanexpress.unify.flowret.ProcessContext;
import com.americanexpress.unify.flowret.UnitType;

public class PharmacyComponentFactory implements ProcessComponentFactory {
    @Override
    public Object getObject(ProcessContext pc) {
        String compName = pc.getCompName();
        if (pc.getCompType() == UnitType.STEP) {
            switch (compName) {
                case "check_prescriptions":
                    return new CheckPrescriptionsStep(pc);
                case "send_notification":
                    return new SendNotificationStep(pc);
                case "wait_response":
                    return new WaitResponseStep(pc);
                case "throw_exception":
                    return new ThrowExceptionStep(pc);
                case "start":
                    return new StartStep(pc);
                case "end":
                    return new EndStep(pc);
                default:
                    throw new RuntimeException("Unknown step component: " + compName);
            }
        } else if (pc.getCompType() == UnitType.S_ROUTE) {
            switch (compName) {
                case "prescriptions_decision":
                    return new PrescriptionsDecisionRoute(pc);
                case "response_decision":
                    return new ResponseDecisionRoute(pc);
                case "handle_no_response":
                    return new HandleNoResponseRoute(pc);
                default:
                    throw new RuntimeException("Unknown route component: " + compName);
            }
        }
        throw new RuntimeException("Unknown component type: " + pc.getCompType());
    }
}
