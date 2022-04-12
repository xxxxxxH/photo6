package fear.of.god.adapter

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import fear.of.god.R
import fear.of.god.utils.loadCircle

class CommonAdapter(data: MutableList<Bitmap>?) :
    BaseQuickAdapter<Bitmap, BaseViewHolder>(R.layout.layout_item, data) {
    override fun convert(holder: BaseViewHolder, item: Bitmap) {
        val imageView = holder.getView<ImageView>(R.id.itemImage)
        Glide.with(context).load(item).into(imageView)
    }
}