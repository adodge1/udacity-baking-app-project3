package com.example.android.bakingapp.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.widget.Button;

import com.example.android.bakingapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {


    /**
     * Builds the URL used to query JSON Feed
     *
     * @return The URL to use to query the Recipies.
     * https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json
     */
    public static URL buildUrl(String movieDBSortBy, String movieDBapiKEY) {
        Uri.Builder builder = new Uri.Builder();

            builder.scheme("https")
                    .authority("d17h27t6h515a5.cloudfront.net")
                    .appendPath("topher")
                    .appendPath("2017")
                    .appendPath("May")
                    .appendPath("59121517_baking")
                    .appendPath("baking.json");
        URL url = null;
        try {
            url = new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    /**
     * Determine if there is an internet connection available.
     *
     * @return true if there is an internet connection, false if there isn't.
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Creates and displays an alert dialog telling the user
     * there is no internet connection
     *
     * @param context The context
     */
    public static void createNoConnectionDialog(Context context) {

        //Create dialog builder with corresponding settings
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        // Set content
        builder.setTitle(context.getString(R.string.network_dialog_title))
                .setMessage(context.getString(R.string.network_dialog_message));
        // Set button
        builder.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        // Create dialog and display it to the user
        AlertDialog dialog = builder.create();

        dialog.show();

        Button okButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        okButton.setTextColor(Color.RED);
    }

}
