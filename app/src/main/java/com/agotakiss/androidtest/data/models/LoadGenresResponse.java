package com.agotakiss.androidtest.data.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class LoadGenresResponse implements Serializable {

    @SerializedName("genres")
    private List<GenreDataModel> genres;

    public List<GenreDataModel> getGenres() {
        return genres;
    }
}
