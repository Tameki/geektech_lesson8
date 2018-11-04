package com.geektech.widgetlesson.widgets.basic;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.geektech.widgetlesson.MainActivity;
import com.geektech.widgetlesson.R;

import java.util.Random;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link BaseWidgetConfigureActivity BaseWidgetConfigureActivity}
 */
public class BaseWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context,
                                AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        CharSequence widgetText = BaseWidgetConfigureActivity.loadTitlePref(context, appWidgetId);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(
                context.getPackageName(),
                R.layout.base_widget
        );

        views.setTextViewText(R.id.appwidget_text, widgetText);

        views.setOnClickPendingIntent(
                R.id.appwidget_container,
                getIntent(context, MainActivity.class, appWidgetId)
        );

        views.setOnClickPendingIntent(
                R.id.appwidget_settings,
                getIntent(context, BaseWidgetConfigureActivity.class, appWidgetId)
        );

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static <T> PendingIntent getIntent(
            Context context,
            Class<T> clazz,
            int widgetId){
        Intent intent = new Intent(context, clazz);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        Random generator = new Random();
        int rand = generator.nextInt();
//        int rand = (int) System.currentTimeMillis() / 1000;

        return PendingIntent.getActivity(
                context,
                rand,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int id : appWidgetIds) {
            Log.d("ololo", "Update widget - " + id);
        }

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        Log.d("ololo", "New width " +
                newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH));
        Log.d("ololo", "New height " +
                newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT));
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        Log.d("ololo", "Delete widgets " + appWidgetIds);
        for (int appWidgetId : appWidgetIds) {
            BaseWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
        }
    }
}

