package fear.of.god.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import fear.of.god.R

class IRadioGroup: LinearLayout{

    private var group:RadioGroup?=null
    private var radio1:RadioButton?=null
    private var radio2:RadioButton?=null
    private var radio3:RadioButton?=null

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context)
    }

    private fun initView(context: Context) :View{
        val v = LayoutInflater.from(context).inflate(R.layout.layout_rg, this, true)
        group = v.findViewById(R.id.group)
        radio1 = v.findViewById(R.id.radio1)
        radio2 = v.findViewById(R.id.radio2)
        radio3 = v.findViewById(R.id.radio3)
        return v
    }

    fun getRadio1():RadioButton{
        return radio1!!
    }

    fun getRadio2():RadioButton{
        return radio2!!
    }

    fun getRadio3():RadioButton{
        return radio3!!
    }

    fun getRadioGroup():RadioGroup{
        return group!!
    }
}