package com.demo.demoapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.demo.demoapp.R;
import com.demo.demoapp.constants.AppConstants;
import com.demo.demoapp.entities.Movie;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView txtTitle, txtOverView, txtVotes, txtAdult, txtReleaseDate;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        setTitle(getString(R.string.title_movie_details));
        initUI();
        getIntentExtra();
        populateUI();
    }

    private void getIntentExtra() {

        if (getIntent() != null && getIntent().hasExtra(AppConstants.KEY_MOVIE))
            movie = getIntent().getParcelableExtra(AppConstants.KEY_MOVIE);
    }

    private void initUI() {

        txtTitle = findViewById(R.id.txt_title);
        txtOverView = findViewById(R.id.txt_overview);
        txtVotes = findViewById(R.id.txt_votes);
        txtAdult = findViewById(R.id.txt_adult);
        txtReleaseDate = findViewById(R.id.txt_release_date);
    }

    private void populateUI() {

        if (movie == null)
            return;

        txtTitle.setText(movie.getTitle());
        txtOverView.setText(movie.getOverview());
        txtVotes.setText(String.format(getString(R.string.average_votes), movie.getVoteAverage()));
        txtAdult.setText(String.format(getString(R.string.adult_movie), movie.isAdult() ? getString(R.string.yes) : getString(R.string.no)));
        txtReleaseDate.setText(String.format(getString(R.string.release_date), movie.getReleaseDateString()));
    }

}
