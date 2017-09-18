package com.popularmovie.sasin.popularmovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private RecyclerView moviePictueRecycleView;
    private MovieRecycleViewAdapter moviePictureAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign RecycleView
        moviePictueRecycleView = (RecyclerView) findViewById(R.id.movie_picture_recycle_view);

        //Create Layout Manager
        GridLayoutManager movieGridLayoutManager = new GridLayoutManager(this,2);
        moviePictueRecycleView.setLayoutManager(movieGridLayoutManager);

        String[] DataSet = {"A1","A2","A3","A4","A5","A6","A7","A8","A9","A10","A11","A12"};
        moviePictureAdapter = new MovieRecycleViewAdapter(DataSet);
        moviePictueRecycleView.setAdapter(moviePictureAdapter);

        URL movieAPI = MovieNetworkUtils.buildUrl("54673070422b0dffc26a43c1ca31fa94",0);
//        try {
//            String response = MovieNetworkUtils.getResponseFromHttpUrl(movieAPI);
            Log.d("Response",movieAPI.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //Create Adapter Class
    }
}
