package com.c2tarun.cameramon.util;

import android.util.Log;

public class ExecutionTimer {

    private String operation;
    private long start;
    private long end;

    public ExecutionTimer(String operation) {
        this.operation = operation;
    }

    public void start() {
        this.start = System.currentTimeMillis();
    }

    public void stop() {
        this.end = System.currentTimeMillis();
        Log.d(operation, " took " + (end - start) + " milliseconds");
    }
}
