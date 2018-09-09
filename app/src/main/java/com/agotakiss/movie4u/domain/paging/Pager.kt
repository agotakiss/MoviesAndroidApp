package com.agotakiss.movie4u.domain.paging

import io.reactivex.Single

class Pager<T> constructor(
    private val sourceProvider: (Int, Any?) -> Single<PagedResult<T>>,
    val param: Any? = null
) {

    private var currentPage = 1
    private var totalPages: Int? = null

    fun getNextPage(): Single<List<T>> {
        totalPages?.let { total ->
            if (total < currentPage) return Single.just(emptyList())
        }

        return sourceProvider.invoke(currentPage++, param)
            .map {
                totalPages = it.totalPages
                it.data
            }
    }
}