package com.sedat.themovieapp.clicklistener

import android.view.View
import com.sedat.themovieapp.model.Result

interface MyClickListener {
    fun onSearchItemClick(view: View, movie_id: Int)

}

interface FavouriteBtnClickListener{
    fun onFavouriteBtnClick(view: View, movie: Result)
}