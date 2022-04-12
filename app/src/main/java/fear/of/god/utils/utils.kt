package fear.of.god.utils

import android.Manifest
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hjq.permissions.XXPermissions
import com.jhworks.library.ImageSelector
import com.jhworks.library.core.MediaSelectConfig
import com.roger.catloadinglibrary.CatLoadingView
import fear.of.god.R
import fear.of.god.page.business.*
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

var targetClass = ""

var selectImage = ""

fun AppCompatActivity.requestPermission(agree: () -> Unit, disagree: () -> Unit) {
    XXPermissions.with(this)
        .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
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
            .setShowCamera(false)
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

fun AppCompatActivity.showToast(content: String, type: ToastType, duration: Long = MotionToast.SHORT_DURATION) {
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