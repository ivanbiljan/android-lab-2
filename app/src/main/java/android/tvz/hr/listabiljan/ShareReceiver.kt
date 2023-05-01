package android.tvz.hr.listabiljan

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ShareReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.tvz.hr.listabiljan.BROADCAST") {
            Toast.makeText(context, "Content shared", Toast.LENGTH_SHORT).show()
        }
    }
}