package com.popularmovie.sasin.popularmovie;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieRecycleViewAdapter.MovieItemClickListener{

    private RecyclerView moviePictureRecycleView;
    private MovieRecycleViewAdapter moviePictureAdapter;
    private ProgressBar mLoadingIndicator;


    JSONMovieConverter JSONMovie;

    String responseData = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        moviePictureRecycleView = (RecyclerView) findViewById(R.id.movie_picture_recycle_view);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);



        GridLayoutManager movieGridLayoutManager = new GridLayoutManager(this,2);
        moviePictureRecycleView.setLayoutManager(movieGridLayoutManager);

        moviePictureAdapter = new MovieRecycleViewAdapter(this);


        URL movieAPI = MovieNetworkUtils.buildUrl(0);
        new  MovieQueryTask().execute(movieAPI);

        Log.d("R",movieAPI.toString());
    }

    public void OnFinishFetchData() throws JSONException {
        Log.d("R",responseData);

        moviePictureAdapter.UpdateAdapterWithFetchData(JSONMovie.GetMovieURLs(),JSONMovie.GetNames());
        moviePictureRecycleView.setAdapter(moviePictureAdapter);
    }

    @Override
    public void OnClickViewHolder(int viewHolderPosition) {

        Context context = MainActivity.this;
        Class destinationActivity = MovieInfromationActivity.class;
        Intent childIntentData = new Intent(context, destinationActivity);
        String movieSelectID = "0";
        try {
            movieSelectID = JSONMovie.GetSelectMovieID(viewHolderPosition);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        childIntentData.putExtra("MOVIE_ID",movieSelectID);

        startActivity(childIntentData);
    }


    public void PrintError()
    {
        Log.e("Error Message","Error Async");
    }

    public class MovieQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
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
            if (fetchAPIResult != null && !fetchAPIResult.equals("")) {
                mLoadingIndicator.setVisibility(View.INVISIBLE);
                JSONMovie = new JSONMovieConverter(fetchAPIResult);
                try {
                    OnFinishFetchData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                PrintError();
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
            URL movieAPI = MovieNetworkUtils.buildUrl(0);
            new  MovieQueryTask().execute(movieAPI);
            return true;
        }
        if (itemThatWasClickedId == R.id.action_sort_top_rate) {

            URL movieAPI = MovieNetworkUtils.buildUrl(1);
            new  MovieQueryTask().execute(movieAPI);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
