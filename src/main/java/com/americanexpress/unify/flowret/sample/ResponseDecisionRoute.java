package com.americanexpress.unify.flowret.sample;

import com.americanexpress.unify.flowret.*;
import java.util.ArrayList;
import java.util.List;

public class ResponseDecisionRoute implements InvokableRoute {
    private ProcessContext pc;

    public ResponseDecisionRoute(ProcessContext pc) {
        this.pc = pc;
    }

    @Override
    public RouteResponse executeRoute() {
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
