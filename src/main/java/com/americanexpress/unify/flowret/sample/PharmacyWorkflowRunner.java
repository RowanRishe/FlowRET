package com.americanexpress.unify.flowret.sample;

import com.americanexpress.unify.base.BaseUtils;
import com.americanexpress.unify.base.UnifyException;
import com.americanexpress.unify.flowret.ERRORS_FLOWRET;
import com.americanexpress.unify.flowret.Flowret;
import com.americanexpress.unify.flowret.Rts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PharmacyWorkflowRunner {
    private static Logger logger = LoggerFactory.getLogger(PharmacyWorkflowRunner.class);
    public static final String DIR_PATH = "C:/temp/flowret/";

    public static void main(String[] args) {
        // Clear out the base directory
        deleteFiles(DIR_PATH);

        // Initialize Flowret
        ERRORS_FLOWRET.load();
        Flowret.init(10, 30000, "-");
        Flowret.instance().setWriteAuditLog(true);
        Flowret.instance().setWriteProcessInfoAfterEachStep(true);

        // Wire up the Flowret runtime service using pharmacy-specific components.
        PharmacyFlowretDao dao = new PharmacyFlowretDao(DIR_PATH);
        PharmacyComponentFactory factory = new PharmacyComponentFactory();
        PharmacyEventHandler handler = new PharmacyEventHandler();
        Rts rts = Flowret.instance().getRunTimeService(dao, factory, handler, null);

        // Load the process definition JSON from resources.
        String json = BaseUtils.getResourceAsString(PharmacyWorkflowRunner.class, "/pharmacy_lsa_notification.json");

        // Start the process with a unique case id.
        rts.startCase("pharmacy_case_1", json, null, null);

        // Resume the case until it completes.
        try {
            while (true) {
                logger.info("\n");
                rts.resumeCase("pharmacy_case_1");
            }
        } catch (UnifyException e) {
            logger.error("Process completed or error occurred: " + e.getMessage());
        }
    }

    public static void deleteFiles(String dirPath) {
        try {
            Files.walk(Paths.get(dirPath))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            logger.error("Exception -> " + e.getMessage());
        }
    }
}
