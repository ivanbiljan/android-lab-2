package android.tvz.hr.listabiljan

import android.os.Bundle
import android.tvz.hr.listabiljan.databinding.ActivityNotificationBinding
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class NotificationViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        Log.e("NOTIFICATION-TAG", "onCreate: $title, $content")
        binding.activityNotificationTitle.text = title
        binding.activityNotificationContent.text = content
    }
}