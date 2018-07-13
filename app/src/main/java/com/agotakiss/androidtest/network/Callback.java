package com.agotakiss.androidtest.network;

public interface Callback <T> {
    void onSuccess(T data);
    void onError(Throwable t);
}
