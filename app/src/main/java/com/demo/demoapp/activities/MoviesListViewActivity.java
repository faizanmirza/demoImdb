package com.demo.demoapp.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.demo.demoapp.R;
import com.demo.demoapp.adapters.MoviesAdapter;
import com.demo.demoapp.entities.Movie;
import com.demo.demoapp.listeners.EndlessRecyclerViewScrollListener;
import com.demo.demoapp.network.FetchTopRatedMoviesTask;
import com.demo.demoapp.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.HORIZONTAL;

public class MoviesListViewActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private List<Movie> movieList = new ArrayList<>();
    private EndlessRecyclerViewScrollListener scrollListener;
    private RecyclerView rvMovies;
    private MoviesAdapter moviesAdapter;
    private int currentPage = 1;
    private View container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list_view);
        setTitle(getString(R.string.title_top_rated_movies));
        container = findViewById(android.R.id.content);
        setupMoviesRecyclerView();
        fetchMovies(currentPage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    private void setupMoviesRecyclerView() {

        rvMovies = findViewById(R.id.rv_movies);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, HORIZONTAL);
        rvMovies.addItemDecoration(dividerItemDecoration);
        rvMovies.setLayoutManager(linearLayoutManager);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                fetchMovies(page);
            }
        };

        rvMovies.addOnScrollListener(scrollListener);
    }

    private void fetchMovies(int page) {

        try {

            currentPage = page;

            if (!Utility.isNetworkAvailable(this)) {
                showNetworkError();
                return;
            }

            FetchTopRatedMoviesTask fetchTopRatedMoviesTask = new FetchTopRatedMoviesTask();
            movieList.addAll(fetchTopRatedMoviesTask.execute(currentPage).get());

            if (moviesAdapter == null) {
                moviesAdapter = new MoviesAdapter(this, movieList);
                rvMovies.setAdapter(moviesAdapter);
            } else {

                rvMovies.post(new Runnable() {
                    public void run() {
                        moviesAdapter.notifyItemInserted(movieList.size() - 1);
                    }
                });
            }
        } catch (Exception exception) {

            exception.printStackTrace();
        }
    }

    private void showNetworkError() {

        Snackbar.make(container, R.string.error_connect_to_internet, Snackbar.LENGTH_INDEFINITE).setAction(R.string.retry, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchMovies(currentPage);
            }
        }).setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        moviesAdapter.getFilter().filter(query);
        return true;
    }
}