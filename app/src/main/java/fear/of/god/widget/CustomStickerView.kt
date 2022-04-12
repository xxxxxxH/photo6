package fear.of.god.widget

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.lcw.library.stickerview.Sticker
import com.lcw.library.stickerview.StickerLayout
import fear.of.god.R
import fear.of.god.utils.ResourceUtils
import fear.of.god.utils.ToastType
import fear.of.god.utils.loading
import fear.of.god.utils.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CustomStickerView : LinearLayout {

    private var mainRel: RelativeLayout? = null
    private var image: ImageView? = null
    private var stickerLayout: StickerLayout? = null

    private val loadingView by lazy {
        loading()
    }

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context)
    }

    private fun initView(context: Context): View {
        val v = LayoutInflater.from(context).inflate(R.layout.layout_custom, this, true)
        mainRel = v.findViewById(R.id.mainRel)
        image = v.findViewById(R.id.image)
        stickerLayout = v.findViewById(R.id.stickerLayout)
        return v
    }

    fun setImageBg(url: String) {
        Glide.with(context).load(url).into(image!!)
    }

    fun addSticker(b:Bitmap){
        stickerLayout?.addSticker(Sticker(context, b))
    }

    fun setBitmap(b: Bitmap){
        image!!.setImageBitmap(b)
    }

    fun save() {
        loadingView.show((context as AppCompatActivity).supportFragmentManager, "")
        (context as AppCompatActivity).lifecycleScope.launch(Dispatchers.IO) {
            ResourceUtils.createBitmapFromView(mainRel!!)
            delay(1500)
            withContext(Dispatchers.Main) {
                (context as AppCompatActivity).showToast("success", ToastType.SUCCESS)
                loadingView.dismiss()
            }
        }

    }
}