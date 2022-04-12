package fear.of.god.page

import fear.of.god.R
import fear.of.god.base.BasePage
import kotlinx.android.synthetic.main.layout_privacy_policy.*

class PrivacyPolicyPage:BasePage(R.layout.layout_privacy_policy) {
    override fun start() {
        web.loadUrl("file:///android_asset/privacyPolicy.html")
    }
}