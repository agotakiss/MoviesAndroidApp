package com.agotakiss.androidtest.data.network;

public interface Callback <T> {
    void onSuccess(T data);
    void onError(Throwable t);
}
