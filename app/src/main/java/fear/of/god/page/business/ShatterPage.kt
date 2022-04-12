package fear.of.god.page.business

import android.graphics.Bitmap
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import fear.of.god.R
import fear.of.god.adapter.CommonAdapter
import fear.of.god.base.BasePage
import fear.of.god.utils.*
import kotlinx.android.synthetic.main.layout_pip.*

class ShatterPage:BasePage(R.layout.layout_shatter) , OnItemClickListener {
    private var a: CommonAdapter?=null
    private var isEffect = false
    override fun start() {
        iSticker.setImageBg(selectImage)
        iTitle.setTitle(Constant.TITLE_PIP)
        iTitle.getSaveBtn().click {
            iSticker.save()
        }
        iGroup.getRadio1().apply {
            text = Constant.OPTION_FRAME
            setDrawableTop(R.mipmap.btnframe)
        }
        iGroup.getRadio2().apply {
            text = Constant.OPTION_STICKER
            setDrawableTop(R.mipmap.btnsticker)
        }
        iGroup.getRadio3().apply {
            visibility = View.GONE
        }
        setAdapter()
        iGroup.getRadioGroup().setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.radio1 ->{
                    loadingView.show(supportFragmentManager,"")
                    getResourceBitmap(Constant.ASSETS_SHATTER) {data->
                        a!!.setNewInstance(data)
                        isEffect = false
                        loadingView.dismiss()
                    }
                }
                R.id.radio2 -> {
                    loadingView.show(supportFragmentManager,"")
                    getResourceBitmap(Constant.ASSETS_STICKER) { data ->
                        a!!.setNewInstance(data)
                        isEffect = false
                        loadingView.dismiss()
                    }
                }
            }
        }
    }

    private fun setAdapter(){
        a = CommonAdapter(ArrayList<Bitmap>())
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler.adapter = a
        a!!.setOnItemClickListener(this)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val data = adapter.data as ArrayList<Bitmap>
        val b = data[position]
        if (isEffect){
            iSticker.setBitmap(b)
        }else{
            iSticker.addSticker(b)
        }
    }
}