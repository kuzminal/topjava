package ru.javawebinar.topjava.service.rules;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;

public class RuleBasedTest {
    private static String watchedLog = "";

    @Rule
    public Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            watchedLog += description.getMethodName() + " - success " + nanos + " ns\n";
            System.out.println("Test - " + description.getMethodName() + " - success" + nanos + " ns");
        }
    };

    @AfterClass
    public static void print() {
        System.out.println("\nTest summary:");
        System.out.println(watchedLog);
    }
}
