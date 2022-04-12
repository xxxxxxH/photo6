package fear.of.god.page

import com.bumptech.glide.Glide
import fear.of.god.R
import fear.of.god.base.BasePage
import fear.of.god.utils.*
import kotlinx.android.synthetic.main.layout_image.*

class ImagePage : BasePage(R.layout.layout_image) {
    private val url by lazy {
        intent.getStringExtra("url")
    }

    override fun start() {
        Glide.with(this).load(url).into(image)
        url?.let { u ->
            fb.click { share2Facebook(u) }
            ins.click { share2Ins(u) }
            email.click { share2Email(u) }
            delete.click { removeCache(u) }
        } ?: kotlin.run {
            showToast("no data", ToastType.ERROR)
            finish()
        }
    }
}