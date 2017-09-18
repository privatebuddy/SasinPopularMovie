package com.popularmovie.sasin.popularmovie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Moondance on 9/18/2017 AD.
 */

public class MovieRecycleViewAdapter extends RecyclerView.Adapter<MovieRecycleViewAdapter.MoviePictureViewHolder> {

    public String[] MoviePicturePaths;

    public MovieRecycleViewAdapter(String[] adapterDataSet)
    {
        MoviePicturePaths = adapterDataSet;
    }

    @Override
    public MoviePictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_picture, parent, false);

        MoviePictureViewHolder newViewHolder = new MoviePictureViewHolder(v);

        return newViewHolder;
    }

    @Override
    public void onBindViewHolder(MoviePictureViewHolder holder, int position) {

        Picasso.with(holder.mMovieImageView.getContext()).load("http://image.tmdb.org/t/p/w500/"+MoviePicturePaths[position]).into(holder.mMovieImageView);
        holder.mMovieNameTextView.setText("This is "+MoviePicturePaths[position]);
    }

    @Override
    public int getItemCount() {
        return MoviePicturePaths.length;
    }

    public static class MoviePictureViewHolder extends RecyclerView.ViewHolder {

        public TextView mMovieNameTextView;
        public ImageView mMovieImageView;

        public MoviePictureViewHolder(View view) {
            super(view);
            mMovieImageView = view.findViewById(R.id.movie_picture_view);
            mMovieNameTextView = view.findViewById(R.id.movie_name_text_view);
        }
    }

}
