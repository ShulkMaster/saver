package com.sovize.saver.utilities


import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException

class FileManager {

    private val tag = "FileManager"

    /* Checks if external storage is available for read and write */
    fun isExternalStorageWritable(): Boolean = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED


    /* Checks if external storage is available to at least read */
    private fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }

    fun makeTxtFIle(filename: String, ctx: Context): File {
        // Get the directory for the user's public download directory.
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename)
        if (isExternalStorageWritable()) {
            if (!file.exists()) {
                try {
                    file.mkdirs()
                    file.createNewFile()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                Log.i(tag, "Directory didn't exist, created just now ")
            }
            Log.d(tag, "this is the path: ${file.absolutePath}")
        } else{
            Log.w(tag, "External media is not writable")
            Toast.makeText(ctx, "External media is not writable by this app", Toast.LENGTH_LONG)
                .show()
        }
        return file
    }

    fun writeFile(file: File, content: String){
        if(isExternalStorageWritable()){
            FileWriter(file).write(content)
            FileOutputStream(file).write(content.toByteArray())
        }
    }

    fun getUri(path: String, context: Context): Uri {
        return FileProvider.getUriForFile(context, "com.sovize.saver.fileprovider", File(path))
    }
}