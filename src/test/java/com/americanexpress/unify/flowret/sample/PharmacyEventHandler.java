package com.americanexpress.unify.flowret.sample;

import com.americanexpress.unify.flowret.EventHandler;
import com.americanexpress.unify.flowret.EventType;
import com.americanexpress.unify.flowret.ProcessContext;

public class PharmacyEventHandler implements EventHandler {
    @Override
    public void invoke(EventType event, ProcessContext pc) {
        System.out.println("Event received: " + event + " for component: " + pc.getCompName());
    }
}
