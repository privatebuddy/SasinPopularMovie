package com.popularmovie.sasin.popularmovie;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private RecyclerView moviePictueRecycleView;
    private MovieRecycleViewAdapter moviePictureAdapter;

    private Menu settingMenu;
    String responseData = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        URL movieAPI = MovieNetworkUtils.buildUrl("54673070422b0dffc26a43c1ca31fa94",0);
        new  MovieQueryTask().execute(movieAPI);

        moviePictueRecycleView = (RecyclerView) findViewById(R.id.movie_picture_recycle_view);

        GridLayoutManager movieGridLayoutManager = new GridLayoutManager(this,2);
        moviePictueRecycleView.setLayoutManager(movieGridLayoutManager);

//
        Log.d("R",movieAPI.toString());

        String[] DataSet = {"A1","A2","A3","A4","A5","A6","A7","A8","A9","A10","A11","A12"};
        moviePictureAdapter = new MovieRecycleViewAdapter(DataSet);
        moviePictueRecycleView.setAdapter(moviePictureAdapter);


    }

    public void DoAfterLoad() throws JSONException {
        Log.d("R",responseData);
        JSONObject convertObject = null;
        try {
            convertObject  = new JSONObject(responseData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray arrayMovie = convertObject.getJSONArray("results");


        String[] DataSet = new String[arrayMovie.length()];
        for (int i = 0;i < arrayMovie.length();i++)
        {
            JSONObject object = arrayMovie.getJSONObject(i);

            DataSet[i] = object.getString("poster_path").toString();
        }

        moviePictureAdapter = new MovieRecycleViewAdapter(DataSet);
        moviePictueRecycleView.setAdapter(moviePictureAdapter);
    }

    public class MovieQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL fetchAPIURL = params[0];
            try {
                responseData = MovieNetworkUtils.getResponseFromHttpUrl(fetchAPIURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseData;
        }

        @Override
        protected void onPostExecute(String fetchAPIResult) {
//            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (fetchAPIResult != null && !fetchAPIResult.equals("")) {
                try {
                    DoAfterLoad();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
//                showErrorMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();

        if (itemThatWasClickedId == R.id.action_sort_popular) {
            URL movieAPI = MovieNetworkUtils.buildUrl("54673070422b0dffc26a43c1ca31fa94",0);
            new  MovieQueryTask().execute(movieAPI);
            return true;
        }
        if (itemThatWasClickedId == R.id.action_sort_top_rate) {
            URL movieAPI = MovieNetworkUtils.buildUrl("54673070422b0dffc26a43c1ca31fa94",1);
            new  MovieQueryTask().execute(movieAPI);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
