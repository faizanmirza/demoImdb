package com.demo.demoapp.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.demo.demoapp.R;

public class MoviesViewHolder extends RecyclerView.ViewHolder {

    public TextView txtTitle, txtOverview, txtReleaseDate;
    public View view;

    public MoviesViewHolder(View view) {
        super(view);
        this.view = view;
        txtTitle = view.findViewById(R.id.txt_title);
        txtOverview = view.findViewById(R.id.txt_overview);
        txtReleaseDate = view.findViewById(R.id.txt_release_date);
    }
}
