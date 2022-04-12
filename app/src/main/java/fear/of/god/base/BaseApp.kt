package fear.of.god.base

import android.app.Application
import com.jhworks.library.ImageSelector
import com.tencent.mmkv.MMKV
import fear.of.god.utils.IGlideEngine

class BaseApp :Application(){
    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
        ImageSelector.setImageEngine(IGlideEngine())
    }
}