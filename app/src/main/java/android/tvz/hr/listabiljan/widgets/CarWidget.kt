package android.tvz.hr.listabiljan.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.tvz.hr.listabiljan.R
import android.tvz.hr.listabiljan.activities.CarDetailsActivity
import android.tvz.hr.listabiljan.infrastructure.api.RetrofitHelper
import android.tvz.hr.listabiljan.infrastructure.models.Car
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.AppWidgetTarget
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CarWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        GlobalScope.launch {
            // Iterate through each widget instance
            for (appWidgetId in appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId)
            }
        }
    }

    private suspend fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        RetrofitHelper.carsApi.getCars().last()?.let {
            val intent = Intent(context, CarDetailsActivity::class.java)
            intent.putExtra(CarDetailsActivity.PARCELABLE_EXTRA_KEY, it)

            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE)

            // Create a remote views object for the widget layout
            val views = RemoteViews(context.packageName, R.layout.car_widget).apply {
                setOnClickPendingIntent(R.id.details_activity, pendingIntent)

                setTextViewText(R.id.widgetCarName, it.name ?: "No Car Available")

                val imageTarget = AppWidgetTarget(context, R.id.widgetCarImage, this, appWidgetId)

                Glide.with(context)
                    .asBitmap()
                    .load(it.imageUrl)
                    .placeholder(R.drawable.ic_car_placeholder)
                    .into(imageTarget)
            }

            // Update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}