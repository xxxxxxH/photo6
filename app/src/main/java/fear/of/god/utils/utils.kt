package fear.of.god.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.hjq.permissions.XXPermissions
import com.jhworks.library.ImageSelector
import com.jhworks.library.core.MediaSelectConfig
import com.roger.catloadinglibrary.CatLoadingView
import com.tencent.mmkv.MMKV
import fear.of.god.BuildConfig
import fear.of.god.R
import fear.of.god.page.business.*
import fear.of.god.widget.GUPUtil
import jp.co.cyberagent.android.gpuimage.GPUImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.io.File


var targetClass = ""

var selectImage = ""

fun AppCompatActivity.requestPermission(agree: () -> Unit, disagree: () -> Unit) {
    XXPermissions.with(this)
        .permission(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        .request { _, all ->
            if (all) {
                agree()
            } else {
                disagree()
            }
        }
}

fun AppCompatActivity.nextPage(clazz: Class<*>) {
    startActivity(Intent(this, clazz))
}

fun AppCompatActivity.nextByTargetClass() {
    var clazz: Class<*>? = null
    when (targetClass) {
        Constant.TYPE_BOKEH -> {
            clazz = BokehPage::class.java
        }
        Constant.TYPE_COLOR -> {
            clazz = ColorPage::class.java
        }
        Constant.TYPE_PIP -> {
            clazz = PipPage::class.java
        }
        Constant.TYPE_PIXEL -> {
            clazz = PixelPage::class.java
        }
        Constant.TYPE_SHATTER -> {
            clazz = ShatterPage::class.java
        }
    }
    nextPage(clazz!!)
}

fun View.click(click: (View) -> Unit) {
    setOnClickListener {
        click(it)
    }
}

fun loading(): CatLoadingView {
    return CatLoadingView()
}

fun AppCompatActivity.openAlbum() {
    ImageSelector.startImageAction(
        this, 11, MediaSelectConfig.Builder()
            .setShowCamera(true)
            .setOpenCameraOnly(false)
            .setMaxCount(1)
            .setPlaceholderResId(R.mipmap.ic_launcher)
            .build()
    )
}

fun AppCompatActivity.openCamera() {
    ImageSelector.startImageAction(
        this, 22, MediaSelectConfig.Builder()
            .setShowCamera(true)
            .setOpenCameraOnly(true)
            .setMaxCount(1)
            .setPlaceholderResId(R.mipmap.ic_launcher)
            .build()
    )
}

@SuppressLint("UseCompatLoadingForDrawables")
fun RadioButton.setDrawableTop(id: Int) {
    val d = this.context.resources.getDrawable(id)
    d.setBounds(0, 0, d.minimumWidth, d.minimumHeight)
    setCompoundDrawables(null, d, null, null)
}

fun AppCompatActivity.getResourceBitmap(path: String, data: (ArrayList<Bitmap>) -> Unit) {
    lifecycleScope.launch(Dispatchers.IO) {
        val result = ArrayList<Bitmap>()
        val imgs = resources.assets.list(path)
        Log.e("xxxxxxH", "path = $path")
        imgs?.let {
            for (item in it) {
                val b = BitmapFactory.decodeStream(resources.assets.open("$path/$item"))
                result.add(b)
            }
        }
        withContext(Dispatchers.Main) {
            data(result)
        }
    }
}

fun AppCompatActivity.getEffectData(data: (ArrayList<Bitmap>) -> Unit) {
    val gpuImage = GPUImage(this)
    val result = ArrayList<Bitmap>()
    lifecycleScope.launch(Dispatchers.IO) {
        for (index in 1 until 19) {
            gpuImage.setImage(BitmapFactory.decodeFile(selectImage))
            gpuImage.setFilter(GUPUtil.createFilterForType(GUPUtil.getFilters().filters[index]))
            result.add(gpuImage.bitmapWithFilterApplied)
        }
        withContext(Dispatchers.Main) {
            data(result)
        }
    }
}

fun ImageView.loadCircle(url: Any) {
    Glide.with(context).load(url)
        .apply(RequestOptions.bitmapTransform(CircleCrop()))
        .into(this)

}

fun AppCompatActivity.getScreenSize(): IntArray {
    val result = IntArray(2)
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    val height = displayMetrics.heightPixels
    val width = displayMetrics.widthPixels
    result[0] = height
    result[1] = width
    return result
}

fun getMyImage(data: (ArrayList<String>) -> Unit, noData: () -> Unit) {
    val keySet = MMKV.defaultMMKV()!!.decodeStringSet("keys") as HashSet?
    val data = ArrayList<String>()
    if (keySet != null) {
        for (item in keySet) {
            MMKV.defaultMMKV()!!.decodeString(item)?.let {
                data.add(it)
            }
        }
    }
    if (data.size > 0) {
        data(data)
    } else {
        noData()
    }
}

fun AppCompatActivity.isInstallApp(uri:String):Boolean{
    val pm = packageManager
    return try {
        pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}

fun AppCompatActivity.share(packageNames: String, path: String){
    val share = Intent(Intent.ACTION_SEND)
    share.setPackage(packageNames)
    val uri =
        Uri.fromFile(File(path))
    share.putExtra(
        Intent.EXTRA_STREAM,
        uri
    )
    share.putExtra(
        Intent.EXTRA_TEXT, """Make more pics with app link 
                                         https://play.google.com/store/apps/details?id=${packageName}"""
    )
    share.type = "image/jpeg"
    share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    startActivity(Intent.createChooser(share, "Share Picture"))
}

fun AppCompatActivity.share2Facebook(path: String){
    try {
        if (isInstallApp("com.facebook.katana")) {
            share("com.facebook.katana", path)
        } else {
            val appPackageName = "com.facebook.katana"
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW, Uri.parse(
                            "market://details?id=$appPackageName"
                        )
                    )
                )
            } catch (anfe: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW, Uri.parse(
                            "https://play.google.com/store/apps/details?id=$appPackageName"
                        )
                    )
                )
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        showToast("share error", ToastType.ERROR)
    }
}

fun AppCompatActivity.share2Ins(path: String){
    try {
        if (isInstallApp("com.instagram.android")) {
            share( "com.instagram.android", path)
        } else {
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=com.instagram.android")
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=com.instagram.android")
                    )
                )
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        showToast("share error", ToastType.ERROR)
    }
}

@SuppressLint("QueryPermissionsNeeded")
fun AppCompatActivity.share2Email(path: String){
    try {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:") // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.app_name))
        intent.putExtra(
            Intent.EXTRA_STREAM,
            Uri.fromFile(File(path))
        )

        intent.putExtra(
            Intent.EXTRA_TEXT, """Make more pics with app link 
     https://play.google.com/store/apps/details?id=${packageName}"""
        )
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(intent, "Share Picture"))
        } else {
            showToast("Mail app have not been installed", ToastType.ERROR)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        showToast("share error", ToastType.ERROR)
    }
}

fun AppCompatActivity.shareInNative(){
    var shareMessage = "Photo Editor" + "" + "\n\nLet me recommend you this application\n\n"
    shareMessage =
        "${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "text/plain"
    shareIntent.putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.app_name))
    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
    startActivity(Intent.createChooser(shareIntent, "choose one"))
}

fun AppCompatActivity.removeCache(url:String){
    val keySet = MMKV.defaultMMKV()!!.decodeStringSet("keys") as HashSet?
    var deleteKey = ""
    if (keySet != null) {
        for (item in keySet) {
            val value = MMKV.defaultMMKV()!!.decodeString(item)
            if (value == url) {
                deleteKey = item
                break
            }
        }
        MMKV.defaultMMKV()!!.removeValueForKey(deleteKey)
    }
    finish()
}

fun AppCompatActivity.showToast(
    content: String,
    type: ToastType,
    duration: Long = MotionToast.SHORT_DURATION
) {
    when (type) {
        ToastType.SUCCESS -> {
            MotionToast.createToast(
                this, "Success", content, MotionToastStyle.SUCCESS,
                MotionToast.GRAVITY_BOTTOM, duration, null
            )
        }
        ToastType.ERROR -> {
            MotionToast.createToast(
                this, "Error", content, MotionToastStyle.ERROR,
                MotionToast.GRAVITY_BOTTOM, duration, null
            )
        }
        ToastType.INFO -> {
            MotionToast.createToast(
                this, "Info", content, MotionToastStyle.INFO,
                MotionToast.GRAVITY_BOTTOM, duration, null
            )
        }
        ToastType.WARNING -> {
            MotionToast.createToast(
                this, "Waring", content, MotionToastStyle.WARNING,
                MotionToast.GRAVITY_BOTTOM, duration, null
            )
        }
    }

}