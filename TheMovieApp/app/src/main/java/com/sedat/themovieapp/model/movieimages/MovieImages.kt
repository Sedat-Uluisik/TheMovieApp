package com.sedat.themovieapp.model.movieimages


data class MovieImages(
        val backdrops: List<Backdrop>,
        val id: Int,
        val logos: List<Logo>,
        val posters: List<Poster>
)