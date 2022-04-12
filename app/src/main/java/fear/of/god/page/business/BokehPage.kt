package fear.of.god.page.business

import android.graphics.Bitmap
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import fear.of.god.R
import fear.of.god.adapter.CommonAdapter
import fear.of.god.base.BasePage
import fear.of.god.utils.*
import kotlinx.android.synthetic.main.layout_bokeh.*


class BokehPage:BasePage(R.layout.layout_bokeh),OnItemClickListener {
    private var a:CommonAdapter?=null
    private var isEffect = false
    override fun start() {
        iSticker.setImageBg(selectImage)
        iTitle.setTitle(Constant.TITLE_BOKEH)
        iTitle.getSaveBtn().click {
            iSticker.save()
        }
        iGroup.getRadio1().apply {
            text = Constant.OPTION_BOKEH
            setDrawableTop(R.mipmap.btnbokeh)
            click {
                loadingView.show(supportFragmentManager,"")
                getResourceBitmap(Constant.ASSETS_BLEND) { data ->
                    a!!.setNewInstance(data)
                    isEffect = false
                    loadingView.dismiss()
                }
            }
        }
        iGroup.getRadio2().apply {
            text = Constant.OPTION_STICKER
            setDrawableTop(R.mipmap.btnsticker)
            click {
                loadingView.show(supportFragmentManager,"")
                getResourceBitmap(Constant.ASSETS_STICKER) { data ->
                    a!!.setNewInstance(data)
                    isEffect = false
                    loadingView.dismiss()
                }
            }
        }
        iGroup.getRadio3().apply {
            text = Constant.OPTION_EFFECT
            setDrawableTop(R.mipmap.btneffect)
            click {
                loadingView.show(supportFragmentManager,"")
                getEffectData {data->
                    a!!.setNewInstance(data)
                    isEffect = true
                    loadingView.dismiss()
                }
            }
        }
        setAdapter()
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