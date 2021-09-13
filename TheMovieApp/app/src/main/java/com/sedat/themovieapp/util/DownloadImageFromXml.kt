package com.sedat.themovieapp.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.downloadImageFromUrl(url: String?, progressDrawable: CircularProgressDrawable){
    val options = RequestOptions().placeholder(progressDrawable)
    Glide.with(context)
            .setDefaultRequestOptions(options)
            .load("https://image.tmdb.org/t/p/w500$url")
            .into(this)
}

fun placeholderProgressBar(context: Context): CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 7F
        centerRadius = 30F
        start()
    }
}

@BindingAdapter("android:downloadfromurl")
fun downloadImage(imageView: ImageView, url: String?){
    imageView.downloadImageFromUrl(url, placeholderProgressBar(imageView.context))
}
