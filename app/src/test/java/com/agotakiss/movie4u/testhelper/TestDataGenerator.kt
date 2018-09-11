package com.agotakiss.movie4u.testhelper

import com.agotakiss.movie4u.domain.models.Movie

fun createTestMovies(): List<Movie> =
    mutableListOf(
        Movie(id = 123, title = "Test Movie1"),
        Movie(id = 1234, title = "Test Movie2"),
        Movie(id = 12345, title = "Test Movie3"),
        Movie(id = 123456, title = "Test Movie4")
    )