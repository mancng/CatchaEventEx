package com.mancng.catchaeventex.Infrastructure;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.mancng.catchaeventex.activities.BaseFragmentActivity;

public class MarshmallowPermissions {

    //Request
    private static final int EXTERNAL_STORAGE_WRITE_PERMISSION_REQUEST_CODE = 10;
    private static final int EXTERNAL_STORAGE_READ_PERMISSION_REQUEST_CODE = 11;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 12;

    private BaseFragmentActivity mActivity;

    public MarshmallowPermissions(BaseFragmentActivity activity) {
        mActivity = activity;
    }

    //Check read permissions
    public boolean checkPermissionForReadExternalStorage() {
        int result = ContextCompat.checkSelfPermission(mActivity, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    //Check write permissions
    public boolean checkPermissionForWriteExternalStorage() {
        int result = ContextCompat.checkSelfPermission(mActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    //Check camera permissions
    public boolean checkPermissionForCamera() {
        int result = ContextCompat.checkSelfPermission(mActivity, android.Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    //Request for read permission
    public void requestPermissionsForReadExternalStorage() {
        //Check to see if the app has asked for permission before and the user has denied it
        if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(mActivity, "Read External Storage Permission is needed. Please turn it on inside the phone settings.", Toast.LENGTH_LONG).show();
        } else {
            //Check if the permission has never been asked before and request for permission
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    EXTERNAL_STORAGE_READ_PERMISSION_REQUEST_CODE);
        }
    }

    //Request for write permission
    public void requestPermissionsForWriteExternalStorage() {
        //Check to see if the app has asked for permission before and the user has denied it
        if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(mActivity, "Write External Storage Permission is needed. Please turn it on inside the phone settings.", Toast.LENGTH_LONG).show();
        } else {
            //Check if the permission has never been asked before and request for permission
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    EXTERNAL_STORAGE_WRITE_PERMISSION_REQUEST_CODE);
        }
    }

    //Request for read permission
    public void requestPermissionsForCamera() {
        //Check to see if the app has asked for permission before and the user has denied it
        if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.CAMERA)) {
            Toast.makeText(mActivity, "Camera Permission is needed. Please turn it on inside the phone settings.", Toast.LENGTH_LONG).show();
        } else {
            //Check if the permission has never been asked before and request for permission
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE);
        }
    }
}
