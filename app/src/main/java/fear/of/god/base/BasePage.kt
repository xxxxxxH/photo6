package fear.of.god.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.roger.catloadinglibrary.CatLoadingView

abstract class BasePage(layoutId:Int) :AppCompatActivity(layoutId){
    protected val loadingView by lazy {
        CatLoadingView()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        start()
    }

    abstract fun start()
}