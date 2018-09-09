package com.agotakiss.movie4u.domain.paging

data class PagedResult<T>(
    val data: List<T>,
    val totalPages: Int
)