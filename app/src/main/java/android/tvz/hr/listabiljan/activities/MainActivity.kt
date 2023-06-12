package android.tvz.hr.listabiljan.activities

import android.tvz.hr.listabiljan.infrastructure.models.Car
import android.app.ActivityOptions
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.tvz.hr.listabiljan.infrastructure.FirebaseNotificationService
import android.tvz.hr.listabiljan.OnCarClickListener
import android.tvz.hr.listabiljan.R
import android.tvz.hr.listabiljan.databinding.ActivityMainBinding
import android.tvz.hr.listabiljan.fragments.DetailsFragment
import android.tvz.hr.listabiljan.fragments.ListFragment
import android.tvz.hr.listabiljan.infrastructure.viewmodels.MainViewModel
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.firebase.messaging.RemoteMessage

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // NOTIF CHANNEL
        val name = getString(R.string.notification_channel_name)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // NOTIF CHANNEL
        notificationManager.createNotificationChannel(channel)

        FirebaseNotificationService.onMessageReceived = { message, context ->
            onNotificationReceived(message, context)
        }

        viewModel.selectedItem.observe(this) { car ->
            if (binding.mainActivityDetailsFragmentContainer == null) {
                val intent = Intent(this, CarDetailsActivity::class.java)
                intent.putExtra(CarDetailsActivity.PARCELABLE_EXTRA_KEY, car)

                val detailsActivityOptions = ActivityOptions.makeCustomAnimation(
                    this,
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
                )
                startActivity(intent, detailsActivityOptions.toBundle())
            }
            else {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    addToBackStack(null)
                    replace<DetailsFragment>(
                        R.id.main_activity_details_fragment_container,
                        args = Bundle().apply {
                            putParcelable("car_key", car)
                        }
                    )
                }
            }
        }

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<ListFragment>(R.id.main_activity_list_fragment_container)
            }
        }
    }

    private fun onNotificationReceived(message: RemoteMessage, context: Context) = runOnUiThread {
        val intent = Intent(context, NotificationViewActivity::class.java)
        intent.putExtra("title", message.notification?.title)
        intent.putExtra("content", message.notification?.body)

        val pendingIntent =  TaskStackBuilder.create(this).run {
            // Add the intent, which inflates the back stack
            addNextIntentWithParentStack(intent)
            // Get the PendingIntent containing the entire back stack
            getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }

        val builder = NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setContentTitle(message.notification?.title)
            setContentText(message.notification?.body)
            priority = NotificationCompat.PRIORITY_DEFAULT
            setContentIntent(pendingIntent)
            setAutoCancel(true)
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                "POST_NOTIFICATIONS"
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            var granted = false
            val pushNotificationPermissionLauncher =
                registerForActivityResult(ActivityResultContracts.RequestPermission()) { g ->
                    granted = g
                }
            if (!granted && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                pushNotificationPermissionLauncher.launch("POST_NOTIFICATIONS")
                return@runOnUiThread
            }
        }
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, builder.build())
    }

    companion object {
        private const val CHANNEL_ID = "hr.tvz.android.listabiljan.firebase"
    }
}