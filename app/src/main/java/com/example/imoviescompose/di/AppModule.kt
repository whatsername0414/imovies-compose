package com.example.imoviescompose.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.example.imoviescompose.common.Constants
import com.example.imoviescompose.data.api.MovieApi
import com.example.imoviescompose.data.local.Database
import com.example.imoviescompose.data.local.FavoriteDao
import com.example.imoviescompose.data.local.MovieDao
import com.example.imoviescompose.data.local.UserDao
import com.example.imoviescompose.data.repository.FavoriteRepositoryImpl
import com.example.imoviescompose.data.repository.MovieRepositoryImpl
import com.example.imoviescompose.data.repository.UserRepositoryImpl
import com.example.imoviescompose.domain.repository.FavoriteRepository
import com.example.imoviescompose.domain.repository.MovieRepository
import com.example.imoviescompose.domain.repository.UserRepository
import com.example.imoviescompose.domain.use_case.MovieUseCases
import com.example.imoviescompose.domain.use_case.get_favorite.GetFavoriteUseCase
import com.example.imoviescompose.domain.use_case.get_movie_details.GetMovieDetailsUseCase
import com.example.imoviescompose.domain.use_case.get_movies.GetMoviesUseCase
import com.example.imoviescompose.domain.use_case.get_user.GetUserUseCase
import com.example.imoviescompose.domain.use_case.update_favorite.UpdateFavoriteUseCase
import com.example.imoviescompose.domain.use_case.update_user.UpdateUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieApi(okHttpClient: OkHttpClient): MovieApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun provideConnectivityManager(
        @ApplicationContext context: Context
    ): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        Database::class.java,
        "imovies"
    ).build()

    @Provides
    @Singleton
    fun provideMovieDao(db: Database) = db.movieDao()

    @Provides
    @Singleton
    fun provideFavoriteDao(db: Database) = db.favoriteDao()

    @Provides
    @Singleton
    fun provideUserDao(db: Database) = db.userDao()

    @Provides
    @Singleton
    fun provideMovieRepositories(
        api: MovieApi,
        favoriteDao: FavoriteDao,
        movieService: MovieDao
    ): MovieRepository {
        return MovieRepositoryImpl(api, movieService, favoriteDao)
    }

    @Provides
    @Singleton
    fun provideFavoriteRepositories(
        favoriteDao: FavoriteDao
    ): FavoriteRepository {
        return FavoriteRepositoryImpl(favoriteDao)
    }

    @Provides
    @Singleton
    fun provideUserRepositories(
        userDao: UserDao
    ): UserRepository {
        return UserRepositoryImpl(userDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(
        movieRepository: MovieRepository,
        favoriteRepository: FavoriteRepository,
        userRepository: UserRepository
    ): MovieUseCases {
        return MovieUseCases(
            getMoviesUseCase = GetMoviesUseCase(movieRepository),
            getMovieDetailsUseCase = GetMovieDetailsUseCase(movieRepository),
            updateFavoriteUseCase = UpdateFavoriteUseCase(favoriteRepository),
            getFavoritesUseCase = GetFavoriteUseCase(favoriteRepository),
            getFavoriteUseCase = GetFavoriteUseCase(favoriteRepository),
            getUserUseCase = GetUserUseCase(userRepository),
            updateUserUseCase = UpdateUserUseCase(userRepository)
        )
    }
}