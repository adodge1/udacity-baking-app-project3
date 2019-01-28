package com.example.android.bakingapp.ui.recipe;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipeListAdapter;
import com.example.android.bakingapp.database.FavoriteEntry;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeFragment  extends Fragment {

    ////FRAGMENT INTERFACE TO ADD CLICK this needs to be implemented on the activity
    public interface OnRecipeSelectedInterface{
        //method called to handle when a recipe is selected
        void onListRecipeSelected(int index ,Recipe recipes);

    }



    private RecyclerView.LayoutManager layoutManager;
    private int mNumberColumns;

    private RecipeViewModel mViewModel;
    private RecipeListAdapter mRecipeListAdapter;

    @BindView(R.id.rv_recipeList)
    RecyclerView mMainRecipeListRV;





    public static RecipeFragment newInstance() {
        return new RecipeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        OnRecipeSelectedInterface listener = (OnRecipeSelectedInterface) getActivity();


        View view = inflater.inflate(R.layout.recipe_fragment, container, false);

        // Bind views
        ButterKnife.bind(this, view);

        mMainRecipeListRV = view.findViewById(R.id.rv_recipeList);
        mRecipeListAdapter = new RecipeListAdapter(listener);
        mMainRecipeListRV.setAdapter(mRecipeListAdapter);
        //https://stackoverflow.com/questions/9279111/determine-if-the-device-is-a-smartphone-or-tablet
        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            // use a grid layout manager
            mNumberColumns = calculateNoOfColumns(getContext());
            layoutManager = new GridLayoutManager(getContext(), mNumberColumns, GridLayoutManager.VERTICAL, false);

        } else {
            layoutManager = new LinearLayoutManager(getActivity());
        }

        mMainRecipeListRV.setLayoutManager(layoutManager);
        mMainRecipeListRV.setVisibility(View.VISIBLE);

        if(NetworkUtils.isNetworkAvailable(getContext())){
            //Build URL
            URL recipeUrl = NetworkUtils.buildUrl();
            //Do HTTPS request on background Thread
            new HTTPrequestBackgroundThread().execute(recipeUrl);
        }else {

                NetworkUtils.createNoConnectionDialog(getContext());
        }


        return view;

    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if(noOfColumns < 3)
            noOfColumns = 3;
        return noOfColumns;
    }

    public class HTTPrequestBackgroundThread extends AsyncTask<URL, Void,  ArrayList<Recipe>> {
        @Override
        protected ArrayList<Recipe> doInBackground(URL... params) {
            URL searchUrl = params[0];
            String responseFromHttpUrl = null;
            ArrayList<Recipe> recipes =null;

            try {
                responseFromHttpUrl = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                recipes = NetworkUtils.extractFeatureFromJson(responseFromHttpUrl);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return recipes;

        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> results) {

            if (results != null) {
                mRecipeListAdapter.setRecipeData(results);


                    /*Recipe recipe = results.get(1);

                    String mRecipeName = recipe.getRecipeName();
                    ArrayList<Ingredient> mRecipeIngredients = recipe.getRecipeIngredients();
                    for (int j = 0; j < mRecipeIngredients.size(); j++) {

                         allIngredients = allIngredients+" "+mRecipeIngredients.get(j).getIngredientName();
                    }*/


                   // FavoriteEntry favoriteEntry = new FavoriteEntry(  "testName","testIngredient other other and other");

                   // mViewModel.insertFavorite(favoriteEntry);

                }


        }

    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);



    }

}
