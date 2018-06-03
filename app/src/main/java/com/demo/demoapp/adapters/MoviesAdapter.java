package com.demo.demoapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.demo.demoapp.R;
import com.demo.demoapp.activities.MovieDetailsActivity;
import com.demo.demoapp.entities.Movie;
import com.demo.demoapp.viewholders.MoviesViewHolder;

import java.util.ArrayList;
import java.util.List;

import static com.demo.demoapp.constants.AppConstants.KEY_MOVIE;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesViewHolder> implements Filterable {

    private List<Movie> moviesList, filteredMovieList;
    private Context context;

    public MoviesAdapter(Context context, List<Movie> moviesList) {

        this.context = context;
        this.filteredMovieList = this.moviesList = moviesList;
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.viewholder_movie, parent, false);
        MoviesViewHolder movieViewHolder = new MoviesViewHolder(view);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, final int position) {

        final Movie movie = filteredMovieList.get(position);
        holder.txtTitle.setText(movie.getTitle());
        holder.txtOverview.setText(movie.getOverview());
        holder.txtReleaseDate.setText(String.format(context.getString(R.string.release_date), movie.getReleaseDateString()));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMoviesDetailActivity(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredMovieList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    filteredMovieList = moviesList;
                } else {

                    ArrayList<Movie> filteredList = new ArrayList<>();

                    for (Movie movie : moviesList) {

                        if (movie.getReleaseDateString().toLowerCase().contains(charString)) {
                            filteredList.add(movie);
                        }
                    }
                    filteredMovieList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredMovieList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredMovieList = (ArrayList<Movie>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    private void openMoviesDetailActivity(Movie movie) {

        Intent openMovieDetailActivity = new Intent(context, MovieDetailsActivity.class);
        openMovieDetailActivity.putExtra(KEY_MOVIE, movie);
        context.startActivity(openMovieDetailActivity);
    }
}