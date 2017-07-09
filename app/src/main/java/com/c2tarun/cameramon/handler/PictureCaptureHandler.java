package com.c2tarun.cameramon.handler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.media.Image;
import android.widget.ImageView;

import com.c2tarun.cameramon.util.ExecutionTimer;

public class PictureCaptureHandler implements PictureCallback {

    private Bitmap previousPicture;
    private Bitmap currentPicture;

    private ImageView previousImageView;
    private ImageView currentImageView;

    public PictureCaptureHandler(ImageView previous, ImageView current) {
        this.previousImageView = previous;
        this.currentImageView = current;
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        ExecutionTimer pictureTakenTimer = new ExecutionTimer("onPictureTaken");
        pictureTakenTimer.start();

        previousPicture = currentPicture;
        currentPicture = BitmapFactory.decodeByteArray(data, 0, data.length);
        updateImageViews();

        pictureTakenTimer.stop();
    }

    private void updateImageViews() {
        if(previousPicture != null)
            previousImageView.setImageBitmap(previousPicture);
        if(currentPicture != null)
            currentImageView.setImageBitmap(currentPicture);
    }
}
