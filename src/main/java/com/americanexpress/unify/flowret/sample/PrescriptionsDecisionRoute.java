package com.americanexpress.unify.flowret.sample;

import com.americanexpress.unify.flowret.InvokableRoute;
import com.americanexpress.unify.flowret.ProcessContext;
import com.americanexpress.unify.flowret.RouteResponse;
import com.americanexpress.unify.flowret.UnitResponseType;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionsDecisionRoute implements InvokableRoute {
    private ProcessContext pc;

    public PrescriptionsDecisionRoute(ProcessContext pc) {
        this.pc = pc;
    }

    @Override
    public RouteResponse executeRoute() {
        // Retrieve the "SLAPrescriptions" variable
        String prescriptions = pc.getProcessVariables().getString("SLAPrescriptions");
        List<String> branch = new ArrayList<>();
        if (prescriptions == null || prescriptions.isEmpty()) {
            branch.add("noSLA");
            System.out.println("No prescriptions require SLA.");
        } else {
            branch.add("hasSLA");
            System.out.println("Prescriptions requiring SLA found.");
        }
        return new RouteResponse(UnitResponseType.OK_PROCEED, branch, "");
    }
}
