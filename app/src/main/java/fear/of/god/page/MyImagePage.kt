package fear.of.god.page

import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import fear.of.god.R
import fear.of.god.adapter.MyImageAdapter
import fear.of.god.base.BasePage
import fear.of.god.utils.ToastType
import fear.of.god.utils.getMyImage
import fear.of.god.utils.showToast
import kotlinx.android.synthetic.main.layout_my.*

class MyImagePage : BasePage(R.layout.layout_my) {
    private var a: MyImageAdapter? = null
    override fun start() {

    }

    override fun onResume() {
        super.onResume()
        getMyImage({
            a = MyImageAdapter(it)
            recycler.layoutManager = GridLayoutManager(this, 3)
            recycler.adapter = a
            a!!.setOnItemClickListener { adapter, view, position ->
                val url = (adapter.data as ArrayList<String>)[position]
                startActivity(Intent(this, ImagePage::class.java).apply {
                    putExtra("url", url)
                })
            }
        }, {
            showToast("no data", ToastType.INFO)
            finish()
        })
    }
}