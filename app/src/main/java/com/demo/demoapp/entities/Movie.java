package com.demo.demoapp.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.demo.demoapp.constants.ApiConstants;
import com.demo.demoapp.constants.AppConstants;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Movie implements Parcelable {

    private String title;
    private String overview;
    private Double voteAverage;
    private boolean isAdult;
    private Date releaseDate;

    public Movie() {

    }

    public void parseJson(JSONObject jsonObject) {

        try {

            title = jsonObject.getString(ApiConstants.KEY_TITLE);
            overview = jsonObject.getString(ApiConstants.KEY_OVERVIEW);
            voteAverage = jsonObject.getDouble(ApiConstants.KEY_VOTE_AVERAGE);
            isAdult = jsonObject.getBoolean(ApiConstants.KEY_ADULT);
            releaseDate = new SimpleDateFormat(ApiConstants.KEY_RELEASE_DATE_FORMAT).parse(jsonObject.getString(ApiConstants.KEY_RELEASE_DATE));

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    protected Movie(Parcel in) {

        title = in.readString();
        overview = in.readString();

        if (in.readByte() == 0) {
            voteAverage = null;
        } else {
            voteAverage = in.readDouble();
        }

        isAdult = in.readByte() != 0;
        releaseDate = (java.util.Date) in.readSerializable();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(title);
        dest.writeString(overview);

        if (voteAverage == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(voteAverage);
        }

        dest.writeByte((byte) (isAdult ? 1 : 0));
        dest.writeSerializable(releaseDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public String getReleaseDateString() {

        return new SimpleDateFormat(AppConstants.KEY_DATE_FORMAT).format(releaseDate);
    }
}
