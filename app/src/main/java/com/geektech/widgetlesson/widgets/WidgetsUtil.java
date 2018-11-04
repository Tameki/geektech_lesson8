package com.geektech.widgetlesson.widgets;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

// Created by askar on 10/30/18.
public class WidgetsUtil {
    //Отправляем широковещательное сообщения для обновления виджетов
    public static <T> void updateWidget(Context context, Class<T> widgetClass){
        Intent intent = new Intent(context, widgetClass);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        int[] ids = AppWidgetManager.getInstance(context)
                .getAppWidgetIds(new ComponentName(context, widgetClass));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);

        context.sendBroadcast(intent);
    }
}
