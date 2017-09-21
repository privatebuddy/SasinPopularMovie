package com.popularmovie.sasin.popularmovie;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Moondance on 9/18/2017 AD.
 */

public class MovieNetworkUtils {

        private static int popular = 0;
        private static int topRate = 1;

        final static String API_BASE =
                "http://api.themoviedb.org/3/movie/";

        final static String API_KEY = "api_key";

        public static URL buildUrl(String apiKey,int type) {

            String baseAPI = null;
            if(type == popular) {
                baseAPI = API_BASE.concat("popular");
            }else if(type == topRate) {
                baseAPI = API_BASE.concat("top_rated");
            }

            Uri builtUri = Uri.parse(baseAPI).buildUpon()
                    .appendQueryParameter(API_KEY, apiKey)
                    .build();

            URL url = null;

            try {
                url = new URL(builtUri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            return url;
        }

    public static URL buildUrlIndividual(String apiKey,String movieID) {

        String baseAPI = API_BASE.concat(movieID);

        Uri builtUri = Uri.parse(baseAPI).buildUpon()
                .appendQueryParameter(API_KEY, apiKey)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
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
}
