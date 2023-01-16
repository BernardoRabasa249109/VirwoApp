package com.benyfrabasa.app.core.di

import android.app.Application
import androidx.room.Room
import com.benyfrabasa.app.data.cache.UserSession
import com.benyfrabasa.app.data.database.UserDatabase
import com.benyfrabasa.app.data.repository.NewsRepositoryImpl
import com.benyfrabasa.app.data.repository.UserRepositoryImpl
import com.benyfrabasa.app.domain.repository.NewsRepository
import com.benyfrabasa.app.domain.repository.UserRepository
import com.benyfrabasa.app.utils.BASE_URL
import com.benyfrabasa.app.utils.DATABASE_NAME
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserDatabase(app: Application) = Room.databaseBuilder(
        app,
        UserDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideUserRepository(db: UserDatabase, userSession: UserSession): UserRepository {
        return UserRepositoryImpl(db.userDao, userSession)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(newsRepository: NewsRepositoryImpl): NewsRepository {
        return newsRepository
    }
}
