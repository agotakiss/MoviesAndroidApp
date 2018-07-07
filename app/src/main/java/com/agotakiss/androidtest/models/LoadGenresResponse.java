package com.agotakiss.androidtest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class LoadGenresResponse implements Serializable {

    @SerializedName("genres")
    private List<Genre> genres;

    public List<Genre> getGenres() {
        return genres;
    }
}
