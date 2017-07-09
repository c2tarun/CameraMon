package com.c2tarun.cameramon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.c2tarun.cameramon.handler.PictureCaptureHandler;
import com.c2tarun.cameramon.util.ExecutionTimer;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();
    private Camera camera = null;
    private ImageView previousImageView;
    private ImageView currentImageView;
    private Camera.PictureCallback pictureCallbackHandler;
    private static boolean isTimerRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        previousImageView = (ImageView) findViewById(R.id.previousImage);
        currentImageView = (ImageView) findViewById(R.id.currentImage);

        pictureCallbackHandler = new PictureCaptureHandler(previousImageView, currentImageView);

        Log.d(TAG, "Looking for Camera");
        final int cameraId = findCamera(Camera.CameraInfo.CAMERA_FACING_BACK);
        Log.d(TAG, "Found camera " + cameraId);

        final SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        camera = Camera.open(cameraId);

        final Button takePicture = (Button) this.findViewById(R.id.button);

        new Timer("TakePicturePeriodicTimer").schedule(new TimerTask() {

            @Override
            public void run() {
                if(!isTimerRunning) {
                    isTimerRunning = true;
                    try {
                        camera.setPreviewDisplay(surfaceView.getHolder());
                        camera.startPreview();
                        camera.takePicture(null, null, pictureCallbackHandler);
                    } catch (IOException e) {
                        Log.d(TAG, e.getMessage());
                    } catch (RuntimeException re) {
                        Log.d(TAG, "RuntimeException happened.");
                    } finally {
                        isTimerRunning = false;
                    }
                }
            }
        }, 700, 1500);
    }

    private int findCamera(int facing) {
        int cameraId = -1;
        int numberOfCamera = Camera.getNumberOfCameras();
        for(int i = 0; i< numberOfCamera; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if(info.facing == facing) {
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Releasing camera");
        camera.release();
    }
}
