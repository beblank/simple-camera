package com.adit.simplecamera

import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.DateFormat
import java.text.ParseException
import java.util.*

/**
 * Created by adit on 11/9/2017.
 */
class Camera {

    companion object {

        val path = Environment.getExternalStorageDirectory().toString()
        val dirPath = "$path/Pictures/SFA_MNC"
        var fOut: OutputStream? = null

        fun saveImage(photo: Bitmap):CharSequence?{
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
                Log.e("Error ", "$e")
            }
            return result
        }

        fun createDir(dir: File):Boolean{
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

}