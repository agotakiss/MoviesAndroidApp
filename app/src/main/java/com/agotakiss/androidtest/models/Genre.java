package com.agotakiss.androidtest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Genre implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;
}
