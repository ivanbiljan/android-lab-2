package android.tvz.hr.listabiljan

import android.os.Bundle
import android.tvz.hr.listabiljan.databinding.ActivityMainBinding
import android.tvz.hr.listabiljan.databinding.ImageActivityBinding
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class ImageActivity : AppCompatActivity() {

    private lateinit var binding: ImageActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ImageActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageView = binding.imageView

        // Get the image URI from the intent
        val imageUri = intent.getStringExtra(ImageActivityExtra)

        // Load the image using Glide or any other image loading library
        Glide.with(this)
            .load(imageUri)
            .into(imageView)
    }
}
