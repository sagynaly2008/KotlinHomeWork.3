package kg.geektech.homework3.activity

import android.net.Uri
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import kg.geektech.homework3.adapter.SelectedImageAdapter
import kg.geektech.homework3.base.BaseActivity
import kg.geektech.homework3.databinding.ActivitySelectedImageBinding

class ActivitySelectedImage : BaseActivity<ActivitySelectedImageBinding>() {

    private val adapter = SelectedImageAdapter()

    override fun inflateVB(inflater: LayoutInflater): ActivitySelectedImageBinding {
        return ActivitySelectedImageBinding.inflate(inflater)
    }

    override fun initListener() {
        val uri: ArrayList<Uri>? = intent.getParcelableArrayListExtra(MainActivity.KEY_IMG)
        if (uri != null) {
            adapter.addImage(uri)
        }
    }

    override fun initView() {
        binding.selectedRecycler.layoutManager = GridLayoutManager(this@ActivitySelectedImage, 3)
        binding.selectedRecycler.adapter = adapter
    }
}