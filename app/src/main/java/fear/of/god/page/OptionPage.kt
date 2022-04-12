package fear.of.god.page

import android.app.Activity
import android.content.Intent
import com.jhworks.library.ImageSelector
import fear.of.god.R
import fear.of.god.base.BasePage
import fear.of.god.utils.*
import kotlinx.android.synthetic.main.layout_option.*


class OptionPage :BasePage(R.layout.layout_option){
    override fun start() {
        bokeh.click {
            targetClass = Constant.TYPE_BOKEH
            openAlbum()
        }
        color.click {
            targetClass = Constant.TYPE_COLOR
            openAlbum()
        }
        pip.click {
            targetClass = Constant.TYPE_PIP
            openAlbum()
        }
        pixel.click {
            targetClass = Constant.TYPE_PIXEL
            openAlbum()
        }
        shatter.click {
            targetClass = Constant.TYPE_SHATTER
            openAlbum()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 11) {
            if (resultCode == Activity.RESULT_OK) {
                val temp = ImageSelector.getSelectResults(data)
                temp?.let {
                    selectImage = it[0]
                }?: kotlin.run {
                    showToast("no image selected", ToastType.ERROR)
                }
                nextByTargetClass()
            }
        }
    }
}