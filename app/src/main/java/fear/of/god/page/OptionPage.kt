package fear.of.god.page

import android.app.Activity
import android.content.Intent
import com.jhworks.library.ImageSelector
import fear.of.god.R
import fear.of.god.base.BasePage
import fear.of.god.utils.ToastType
import fear.of.god.utils.selectImage
import fear.of.god.utils.showToast


class OptionPage :BasePage(R.layout.layout_option){
    override fun start() {

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
            }
        }
    }
}