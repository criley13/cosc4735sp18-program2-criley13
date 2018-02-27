package edu.cs4735.program2;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import android.support.v4.app.Fragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class camera extends Fragment {
    static int MEDIA_TYPE_IMAGE = 1;
    static int MEDIA_TYPE_VIDEO = 2;
    String TAG = "CamFragment";
    cameraPreview mPreview;
    FrameLayout preview;
    Button btn_takepicture;


    public camera() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_camera, container, false);

        preview = (FrameLayout) myView.findViewById(R.id.cameraPreview);

        CameraManager manager = (CameraManager) getActivity().getSystemService((Context.CAMERA_SERVICE));
        try {
            String cameraId = manager.getCameraIdList()[0];
            mPreview = new cameraPreview(getActivity().getApplicationContext(), cameraId);
            preview.addView(mPreview);
        } catch (CameraAccessException e) {
            Log.v (TAG, "Failed to get a camera ID!");
            e.printStackTrace();
        }

        btn_takepicture = (Button) myView.findViewById(R.id.btn_takepicture);
        btn_takepicture.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get an image from the camera
                        if (mPreview != null) {
                            mPreview.TakePicture(getOutputMediaFile(MEDIA_TYPE_IMAGE));
                        }

                    }
                }
        );

        return myView;
    }

    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        //creates a directory in pictures.
        //File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyCameraApp");

        File mediaStorageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

}
