package com.agotakiss.movie4u.utils

import io.reactivex.Single

inline fun <T, R> Single<List<T>>.mapListItems(crossinline mapper: (T) -> R): Single<List<R>> =
    this.toObservable()
        .flatMapIterable { it }
        .map { mapper.invoke(it) }
        .toList()