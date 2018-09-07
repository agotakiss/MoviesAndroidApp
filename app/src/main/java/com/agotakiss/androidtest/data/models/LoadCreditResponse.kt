package com.agotakiss.androidtest.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LoadCreditResponse : Serializable {

    @SerializedName("id")
    val id: Int = 0

    @SerializedName("cast")
    val cast: List<CastApiModel>? = null

    @SerializedName("crew")
    val crew: List<CrewApiModel>? = null
}