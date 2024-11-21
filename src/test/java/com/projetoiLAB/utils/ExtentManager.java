package com.projetoiLAB.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static void initReports() {
        if(extent == null){
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter("evidences/log.html");
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
        }
    }

    public static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }

    public static ExtentTest createTest(String testName) {
        ExtentTest test = extent.createTest(testName);
        extentTest.set(test);
        return test;
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }
}

