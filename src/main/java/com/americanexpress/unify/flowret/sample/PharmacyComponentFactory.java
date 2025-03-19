package com.americanexpress.unify.flowret.sample;

import com.americanexpress.unify.flowret.*;
import com.americanexpress.unify.flowret.sample.*;

public class PharmacyComponentFactory implements ProcessComponentFactory {

    @Override
    public Object getObject(ProcessContext pc) {
        String compName = pc.getCompName();
        if (compName == null) {
            throw new RuntimeException("Component name is null");
        }
        // Normalize for comparison.
        String normalized = compName.toLowerCase();
        if (pc.getCompType() == UnitType.STEP) {
            switch (normalized) {
                case "start":
                    return new StartStep(pc);
                case "check_prescriptions":
                    return new CheckPrescriptionsStep(pc);
                case "send_notification":
                    return new SendNotificationStep(pc);
                case "wait_response":
                    return new WaitResponseStep(pc);
                case "throw_exception":
                    return new ThrowExceptionStep(pc);
                case "end":
                case "endstep":  // Accept both "end" and "endstep"
                    return new EndStep(pc);
                default:
                    throw new RuntimeException("Unknown step component: " + compName);
            }
        } else if (pc.getCompType() == UnitType.S_ROUTE) {
            switch (normalized) {
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
