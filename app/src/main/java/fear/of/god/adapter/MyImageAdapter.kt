package fear.of.god.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import fear.of.god.R

class MyImageAdapter(data: MutableList<String>?) :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.layout_item, data) {
    override fun convert(holder: BaseViewHolder, item: String) {
        val imageView = holder.getView<ImageView>(R.id.itemImage)
        Glide.with(context).load(item).into(imageView)
    }
}