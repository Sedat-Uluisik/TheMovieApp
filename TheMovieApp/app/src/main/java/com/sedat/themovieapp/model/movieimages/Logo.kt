package com.sedat.themovieapp.model.movieimages


import com.google.gson.annotations.SerializedName

data class Logo(
    @SerializedName("aspect_ratio")
    val aspectRatio: Float,
    @SerializedName("file_path")
    val filePath: String,
    val height: Int,
    @SerializedName("iso_639_1")
    val iso6391: String,
    @SerializedName("vote_average")
    val voteAverage: Float,
    @SerializedName("vote_count")
    val voteCount: Int,
    val width: Int
)