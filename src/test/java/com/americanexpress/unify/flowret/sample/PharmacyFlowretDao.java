package com.americanexpress.unify.flowret.sample;

import com.americanexpress.unify.flowret.FlowretDao;
import com.americanexpress.unify.jdocs.Document;

public class PharmacyFlowretDao implements FlowretDao {
    private String directory;

    public PharmacyFlowretDao(String directory) {
        this.directory = directory;
    }

    @Override
    public void write(String key, Document d) {
        // Minimal implementation; for now, just print a message.
        System.out.println("Writing document with key: " + key);
    }

    @Override
    public Document read(String key) {
        // Minimal implementation.
        return null;
    }

    @Override
    public long incrCounter(String key) {
        // Minimal implementation.
        return 0;
    }
}
