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
            saveImage(photo)
        }
    }

    fun saveImage(photo:Bitmap):CharSequence?{
        var file: File? = null
        var result:CharSequence? = null
        val outDir = File(dirPath)
        file = File("$dirPath/${getDate()}.png")
        createDir(outDir)
        try {
            file.createNewFile()
            fOut  = FileOutputStream(file)
                photo.compress(Bitmap.CompressFormat.PNG, 85, fOut)
                result = "image saved"
            fOut?.flush()
            fOut?.close()
        } catch (e:Exception){
            toast("Error : $e")
        }
        return result
    }

    fun createDir(dir:File):Boolean{
        var result:Boolean = false
        if(!dir.exists()){
            dir.mkdir()
        }
        return result
    }

    fun getDate():String{
        var parsed:String? = null
        try {
            val format = DateFormat.getDateTimeInstance()
            val date = Date()
            parsed = format.format(date)
        } catch (pe: ParseException) {
            throw IllegalArgumentException(pe)
        }
        return parsed
    }
}
