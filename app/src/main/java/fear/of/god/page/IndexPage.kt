package fear.of.god.page

import fear.of.god.R
import fear.of.god.base.BasePage
import fear.of.god.utils.ToastType
import fear.of.god.utils.nextPage
import fear.of.god.utils.requestPermission
import fear.of.god.utils.showToast

class IndexPage : BasePage(R.layout.layout_index) {
    override fun start() {
        requestPermission({
            nextPage(MainPage::class.java)
            finish()
        }, {
            showToast("no permission app will finish", ToastType.ERROR)
            finish()
        })
    }
}