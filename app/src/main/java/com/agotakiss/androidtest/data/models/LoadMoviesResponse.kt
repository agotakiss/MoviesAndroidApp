package com.agotakiss.androidtest.data.models

import com.google.gson.annotations.SerializedName

import java.io.Serializable

class LoadMoviesResponse : Serializable {

    @SerializedName("page")
    val page: Int = 0

    @SerializedName("results")
    val movies: List<MovieApiModel>? = null

    @SerializedName("total_results")
    val totalResults: Int = 0

    @SerializedName("total_pages")
    val totalPages: Int = 0
}

