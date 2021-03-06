package com.sovize.saver.utilities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionRequester {

    private val tag = "PermissionRequester"
    private val extStoragePermission = 1

    fun hasExtStoragePermission(asked: Context): Boolean =
        if (
            ContextCompat.checkSelfPermission(asked, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(tag, "Permission is not granted yet")
            false
        } else {
            Log.d(tag, "Permission is granted to you")
            true
        }


    fun askExtStoragePermission(activity: Activity) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            Log.d(tag, "Permission was denied before explanation")
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), extStoragePermission
            )
        } else {
            // No explanation needed, we can request the permission.
            Log.d(tag, "Permission remains block")
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), extStoragePermission
            )
        }

    }
}