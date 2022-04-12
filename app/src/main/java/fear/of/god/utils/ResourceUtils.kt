package fear.of.god.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.view.View
import com.tencent.mmkv.MMKV
import java.io.File
import java.io.FileOutputStream


object ResourceUtils {

    fun createBitmapFromView(view: View) {
        view.clearFocus()
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        if (bitmap != null) {
            val canvas = Canvas(bitmap)
            view.draw(canvas)
            canvas.setBitmap(null)
        }
        val path = File(Environment.getExternalStorageDirectory().path + File.separator)
        val fileName = System.currentTimeMillis().toString()
        val imgFile = File(path, "$fileName.png")
        if (!imgFile.exists())
            imgFile.createNewFile()
        var fos: FileOutputStream? = null
        try {
            if (bitmap == null) return
            fos = FileOutputStream(imgFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.flush()
            fos.close()
            scanNotice(view.context, imgFile)
            saveKeys("keys", fileName)
            MMKV.defaultMMKV()!!.encode(fileName, imgFile.absolutePath)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            fos?.flush()
            fos!!.close()
        }
    }

    fun saveKeys(key: String, keyValues: String) {
        var keys = MMKV.defaultMMKV()!!.decodeStringSet(key)
        if (keys == null) {
            keys = HashSet()
        }
        keys.add(keyValues)
        MMKV.defaultMMKV()!!.encode(key, keys)
    }

    private fun scanNotice(context: Context, file: File) {
        MediaScannerConnection.scanFile(
            context,
            arrayOf(file.absolutePath),
            null,
            object : MediaScannerConnection.MediaScannerConnectionClient {
                override fun onMediaScannerConnected() {}
                override fun onScanCompleted(path: String, uri: Uri) {}
            })
    }

}