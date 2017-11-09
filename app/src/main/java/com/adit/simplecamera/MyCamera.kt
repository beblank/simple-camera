package com.adit.simplecamera

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import kotlinx.android.synthetic.main.activity_my_camera.*
import rebus.permissionutils.PermissionEnum
import rebus.permissionutils.FullCallback
import rebus.permissionutils.AskAgainCallback.UserResponse
import rebus.permissionutils.AskAgainCallback
import org.jetbrains.anko.toast
import rebus.permissionutils.PermissionManager
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.DateFormat
import java.text.ParseException
import java.util.*


class MyCamera : AppCompatActivity() {

    val CAMERA_REQUEST = 1888;
    val path = Environment.getExternalStorageDirectory().toString()
    val dirPath = "$path/Pictures/SFA_MNC"
    var fOut: OutputStream? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_camera)

        button1.setOnClickListener {
            askPermission()
            val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, CAMERA_REQUEST)
        }
    }

    private fun askPermission(){
        PermissionManager.Builder()
                .permission(PermissionEnum.WRITE_EXTERNAL_STORAGE
                            , PermissionEnum.CAMERA)
                .askAgain(true)
                .ask(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
            val photo = data!!.extras.get("data") as Bitmap
            imageView1.setImageBitmap(photo)
            Camera.saveImage(photo)
        }
    }


}
