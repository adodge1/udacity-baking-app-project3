package com.example.android.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;


//this is a service that needs to be declared in the manifest
public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetAdapter(this);
    }


}
