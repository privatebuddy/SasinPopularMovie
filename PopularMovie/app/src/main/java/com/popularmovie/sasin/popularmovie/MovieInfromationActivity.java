package com.popularmovie.sasin.popularmovie;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MovieInfromationActivity extends AppCompatActivity {

    TextView movieName;
    TextView movieRating;
    TextView movieDetail;

    ImageView movieImage;
    JSONObject JSONMovieObject;

    private ProgressBar mLoadingIndicator;
    String responseData = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_infromation);

        movieName = (TextView) findViewById(R.id.movie_detail_name);
        movieRating = (TextView) findViewById(R.id.movie_detail_rating);
        movieDetail = (TextView) findViewById(R.id.movie_detail_detail);
        movieImage = (ImageView)  findViewById(R.id.movie_detail_poster);
        mLoadingIndicator = (ProgressBar)  findViewById(R.id.loading_indicator_movie);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("MOVIE_ID")) {


            String movieID = intentThatStartedThisActivity.getStringExtra("MOVIE_ID");

            URL singleMovieAPI = MovieNetworkUtils.buildUrlIndividual(movieID);
            new SingleMovieQueryTask().execute(singleMovieAPI);
        }
    }

    public void UpdateMovieData()  throws JSONException
    {
        Picasso.with(this).load("http://image.tmdb.org/t/p/w500/"+JSONMovieObject.getString("poster_path")).into(movieImage);
        movieName.setText(JSONMovieObject.getString("original_title"));
        movieRating.setText(JSONMovieObject.getString("vote_average")+"/10");
        movieDetail.setText(JSONMovieObject.getString("overview"));
    }


    public void PrintError()
    {
        Log.e("Error Message","Error Async");
    }

    public class SingleMovieQueryTask extends AsyncTask<URL, Void, String> {

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

                try {
                    JSONMovieObject = new JSONObject(fetchAPIResult);
                    UpdateMovieData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                PrintError();
            }
        }
    }
}
