package com.sedat.themovieapp.hilt

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sedat.themovieapp.MySharedPref
import com.sedat.themovieapp.R
import com.sedat.themovieapp.api.MovieApi
import com.sedat.themovieapp.repo.MovieRepository
import com.sedat.themovieapp.repo.MovieRepositoryInterface
import com.sedat.themovieapp.room.MovieDao
import com.sedat.themovieapp.room.MovieDatabase
import com.sedat.themovieapp.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRetrofit(): MovieApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }

    @Singleton
    @Provides
    fun injectRepository(movieApi: MovieApi, movieDao: MovieDao, mySharedPref: MySharedPref) = MovieRepository(movieApi, movieDao, mySharedPref) as MovieRepositoryInterface

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) =
        Glide.with(context)
            .setDefaultRequestOptions(
                RequestOptions().placeholder(R.drawable.downloading_24).error(R.drawable.error_24)
            )

    @Singleton
    @Provides
    fun injectMySharedPref(@ApplicationContext context: Context) = MySharedPref(context)

    @Singleton
    @Provides
    fun injectRoomDb(@ApplicationContext context: Context) =
            Room.databaseBuilder(
                    context,
                    MovieDatabase::class.java,
                    "MovieDb"
            ).build()

    @Singleton
    @Provides
    fun injectDao(database: MovieDatabase) = database.movieDao()
}