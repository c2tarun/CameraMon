package com.c2tarun.cameramon.util;

import org.opencv.android.OpenCVLoader;

public class DetectMotion {
    static {
        if(!OpenCVLoader.initDebug()) {
            // Handle initialization error
        }
    }
}
