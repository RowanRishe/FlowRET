package com.americanexpress.unify.flowret.sample;

import com.americanexpress.unify.flowret.InvokableRoute;
import com.americanexpress.unify.flowret.ProcessContext;
import com.americanexpress.unify.flowret.RouteResponse;
import com.americanexpress.unify.flowret.UnitResponseType;
import java.util.ArrayList;
import java.util.List;

public class HandleNoResponseRoute implements InvokableRoute {
    private ProcessContext pc;

    public HandleNoResponseRoute(ProcessContext pc) {
        this.pc = pc;
    }

    @Override
    public RouteResponse executeRoute() {
        Integer count = pc.getProcessVariables().getInteger("notificationCount");
        if (count == null) {
            count = 0;
        }
        List<String> branch = new ArrayList<>();
        if (count < 3) {
            branch.add("resend");
            System.out.println("Resending notification. Count: " + count);
        } else {
            branch.add("timeout");
            System.out.println("Timeout reached. Count: " + count);
        }
        return new RouteResponse(UnitResponseType.OK_PROCEED, branch, "");
    }
}
