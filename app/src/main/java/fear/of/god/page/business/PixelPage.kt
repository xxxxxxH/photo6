package fear.of.god.page.business

import android.graphics.Bitmap
import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import fear.of.god.R
import fear.of.god.adapter.ColorAdapter
import fear.of.god.adapter.CommonAdapter
import fear.of.god.base.BasePage
import fear.of.god.utils.*
import kotlinx.android.synthetic.main.layout_pixel.*

class PixelPage:BasePage(R.layout.layout_pixel) , OnItemClickListener {
    private var a: CommonAdapter?=null
    private var isEffect = false
    override fun start() {
        iSticker.setImageBg(selectImage)
        iTitle.setTitle(Constant.TITLE_PIXEL)
        iTitle.getSaveBtn().click {
            iSticker.save()
        }
        iGroup.getRadio1().apply {
            text = Constant.OPTION_EFFECT
            setDrawableTop(R.mipmap.btneffect)
        }
        iGroup.getRadio2().apply {
            text = Constant.OPTION_COLOR
            setDrawableTop(R.mipmap.btncolor)
        }
        iGroup.getRadio3().apply {
            text = Constant.OPTION_STICKER
            setDrawableTop(R.mipmap.btnsticker)
        }

        setAdapter()
        iGroup.getRadioGroup().setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.radio1 ->{
                    recycler.visibility = View.VISIBLE
                    recycler2.visibility = View.GONE
                    loadingView.show(supportFragmentManager,"")
                    getResourceBitmap(Constant.ASSETS_PIXEL) {data->
                        a!!.setNewInstance(data)
                        isEffect = false
                        loadingView.dismiss()
                    }
                }
                R.id.radio2 -> {
                    recycler.visibility = View.GONE
                    recycler2.visibility = View.VISIBLE
                    loadingView.show(supportFragmentManager,"")
                    val data = this.resources.getStringArray(R.array.color).toList() as ArrayList<String>
                    val aa = ColorAdapter(data)
                    recycler2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    recycler2.adapter = aa
                    aa.setOnItemClickListener { adapter, view, position ->
                        val bitmap = Bitmap.createBitmap(
                            getScreenSize()[1] / 2,
                            getScreenSize()[1] / 2,
                            Bitmap.Config.ARGB_8888
                        )
                        val data = (adapter.data as ArrayList<String>)[position]
                        bitmap.eraseColor(Color.parseColor(data))
                        iSticker.setBitmap(bitmap)
                    }
                    loadingView.dismiss()
                }
                R.id.radio3 -> {
                    recycler.visibility = View.VISIBLE
                    recycler2.visibility = View.GONE
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