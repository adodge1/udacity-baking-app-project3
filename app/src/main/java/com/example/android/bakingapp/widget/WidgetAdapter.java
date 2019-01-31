package com.example.android.bakingapp.widget;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.database.AppDatabase;
import com.example.android.bakingapp.database.FavoriteEntry;

import java.util.List;

public class WidgetAdapter implements RemoteViewsService.RemoteViewsFactory  {
    Context mContext;

    LiveData<List<FavoriteEntry>> mRecipeList;


    String[] list = {"Nutella Pie","Brownies","Yellow Cake","Cheesecake"};

    String favoriteName;



    public WidgetAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

       AppDatabase db = AppDatabase.getDatabase(mContext);

        mRecipeList = db.favoriteDao().getAllFavorites();


    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public RemoteViews getViewAt(final int position) {
        final RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);


      /*  mRecipeList.observeForever(new Observer<List<FavoriteEntry>>() {
            @Override
            public void onChanged(@Nullable final List<FavoriteEntry> fave) {
                if (fave != null) {
                    if (fave.size() > 0 ) {
                        for (FavoriteEntry temp : fave) {
                            favoriteName = temp.getRecipeName();

                        }
                    }
                }

            }
        });

        mRecipeList.removeObserver((Observer<List<FavoriteEntry>>)this);*/


        remoteViews.setTextViewText(R.id.widget_tv, list[position]);
        Intent intent =new Intent();
        intent.putExtra(WidgetProvider.WIDGET_KEY_ITEM,list[position]);
        remoteViews.setOnClickFillInIntent(R.id.widget_rv, intent);
        return remoteViews;
    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return list.length;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


}
