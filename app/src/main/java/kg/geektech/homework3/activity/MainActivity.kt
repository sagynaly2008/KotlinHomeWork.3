package kg.geektech.homework3.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import kg.geektech.homework3.R
import kg.geektech.homework3.adapter.Adapter
import kg.geektech.homework3.base.BaseActivity
import kg.geektech.homework3.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding>(), Adapter.Listener {
    private val adapter = Adapter(this)
    private val imageList = arrayListOf<Uri>()

    private var activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val image = result.data?.data
                if (image != null) {
                    adapter.addImage(image)
                }
            }
        }

    override fun inflateVB(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }

    override fun initView() {
        binding.recycler.layoutManager = GridLayoutManager(this@MainActivity, 3)
        binding.recycler.adapter = adapter
    }

    override fun initListener() {
        binding.floatingActionButton.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "image/*"
            intent.putExtra(Intent.ACTION_PICK, true)
            activityResultLauncher.launch(intent)
        }
    }

    private fun openActivity(imageList: ArrayList<Uri>) {
        Intent(this@MainActivity, ActivitySelectedImage::class.java).apply {
            putExtra(KEY_IMG, imageList)
            startActivity(this)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(mainImage: Uri) {
        imageList.addAll(listOf(mainImage))
        if (imageList.size >= 0) with(binding) {
            tvToolBar.text =
                getString(R.string.selected) + " " + imageList.size + getString(R.string.photo)
            tvToolBar.setOnClickListener {
                openActivity(imageList)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun deleteClick(mainImage: Uri) {
        imageList.remove(mainImage)
        if (imageList.size >= 0) with(binding) {
            tvToolBar.text =
                getString(R.string.selected) + " " + imageList.size + getString(R.string.photo)
        }
    }

    companion object {
        const val KEY_IMG = "img"
    }
}
