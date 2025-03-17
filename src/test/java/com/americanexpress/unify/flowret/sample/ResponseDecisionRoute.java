package com.americanexpress.unify.flowret.sample;

import com.americanexpress.unify.flowret.InvokableRoute;
import com.americanexpress.unify.flowret.ProcessContext;
import com.americanexpress.unify.flowret.RouteResponse;
import com.americanexpress.unify.flowret.UnitResponseType;
import java.util.ArrayList;
import java.util.List;

public class ResponseDecisionRoute implements InvokableRoute {
    private ProcessContext pc;

    public ResponseDecisionRoute(ProcessContext pc) {
        this.pc = pc;
    }

    @Override
    public RouteResponse executeRoute() {
        // Retrieve the responseReceived flag using getBoolean().
        Boolean responded = pc.getProcessVariables().getBoolean("responseReceived");
        List<String> branch = new ArrayList<>();
        if (responded != null && responded) {
            branch.add("responded");
            System.out.println("Customer has responded.");
        } else {
            branch.add("notResponded");
            System.out.println("Customer has not responded.");
        }
        return new RouteResponse(UnitResponseType.OK_PROCEED, branch, "");
    }
}
