package com.example.android.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;


import com.example.android.bakingapp.R;
import com.example.android.bakingapp.database.FavoriteEntry;
import com.example.android.bakingapp.ui.recipe.RecipeViewModel;

import java.util.List;

//https://stackoverflow.com/questions/21337220/get-data-from-database-in-android-widget
//https://stackoverflow.com/questions/51973927/android-how-to-access-room-database-from-widget

public class WidgetProvider extends AppWidgetProvider {

public static final String WIDGET_KEY_ITEM = "com.example.widget.WIDGET_KEY_ITEM";
public static final String WIDGET_TOAST_ACTION ="com.example.widget.WIDGET_TOAST_ACTION";


    //to handle the click of each item inside the widget
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(WIDGET_TOAST_ACTION)){
            String  listItem = intent.getStringExtra(WIDGET_KEY_ITEM);
            Toast.makeText(context,listItem,Toast.LENGTH_SHORT).show();
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE); //--> to listen to the widget itself and not the items
        }
        super.onReceive(context, intent);
    }

    //AppWidgetManager middle man between us and the widgets.
    //instances of widget
    //use Remote Views
    //needs to be declared in apps manifest
    //tapping the widget will result in a call to onUpdate method
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //super.onUpdate(context, appWidgetManager, appWidgetIds); not needed

        //this is so when we have two widgets of the same app in the home screen each will have the correct action
        int[] realAppWidgetIds = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context,WidgetProvider.class));
        for(int id:realAppWidgetIds){
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);


            Intent serviceIntent = new Intent(context,WidgetService.class);
            remoteViews.setRemoteAdapter(R.id.widget_lv, serviceIntent);





            // need an intent
            Intent intent = new Intent(context, WidgetProvider.class);

            //intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.setAction(WIDGET_TOAST_ACTION);

            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,realAppWidgetIds);
            //need pending intent
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

            remoteViews.setPendingIntentTemplate(R.id.widget_lv,pendingIntent);

            //appWidgetManager.notifyAppWidgetViewDataChanged(id, R.id.widget_lv);


            appWidgetManager.updateAppWidget(id,remoteViews);


        }
    }
}
