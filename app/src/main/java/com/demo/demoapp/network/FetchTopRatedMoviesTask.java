package com.demo.demoapp.network;

import android.os.AsyncTask;

import com.demo.demoapp.constants.ApiConstants;
import com.demo.demoapp.entities.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchTopRatedMoviesTask extends AsyncTask<Integer, Void, List<Movie>> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Movie> doInBackground(Integer... pageNumber) {

        int currentPage = pageNumber[0];
        HttpURLConnection urlConnection = null;
        List<Movie> movieList = new ArrayList<>();

        try {

            StringBuilder jsonResult = new StringBuilder();
            URL url = new URL(String.format(ApiConstants.API_URL, currentPage));
            urlConnection = (HttpURLConnection) url.openConnection();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;

                while ((line = reader.readLine()) != null) {
                    jsonResult.append(line);
                }

                JSONObject jsonObj = new JSONObject(jsonResult.toString());
                JSONArray moviesArray = jsonObj.getJSONArray(ApiConstants.KEY_RESULTS);

                for (int i = 0; i < moviesArray.length(); i++) {
                    JSONObject movieObject = moviesArray.getJSONObject(i);
                    Movie movie = new Movie();
                    movie.parseJson(movieObject);
                    movieList.add(movie);
                }

                return movieList;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Movie> result) {
        super.onPostExecute(result);
    }
}