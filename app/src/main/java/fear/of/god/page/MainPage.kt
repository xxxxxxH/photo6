package fear.of.god.page

import android.content.Intent
import fear.of.god.R
import fear.of.god.base.BasePage
import fear.of.god.utils.click
import fear.of.god.utils.nextPage
import fear.of.god.utils.openCamera
import fear.of.god.utils.shareInNative
import kotlinx.android.synthetic.main.activity_main.*

class MainPage : BasePage(R.layout.activity_main) {

    override fun start() {
        start.click {
            nextPage(OptionPage::class.java)
        }
        myphoto.click {
            nextPage(MyImagePage::class.java)
        }
        share.click { shareInNative() }

        options.click {
            startActivity(Intent(this, PrivacyPolicyPage::class.java))
        }
        camera.click {
            openCamera()
        }
    }
}