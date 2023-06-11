package android.tvz.hr.listabiljan

import android.content.Context
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseNotificationService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        Log.d(TAG, "Message: ${message.notification?.body}")
        super.onMessageReceived(message)
        onMessageReceived?.invoke(message, this)
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
    }

    companion object {
        private const val TAG = "FirebaseNotificationToken"
        var onMessageReceived: ((RemoteMessage, context: Context) -> Unit)? = null
    }
}

