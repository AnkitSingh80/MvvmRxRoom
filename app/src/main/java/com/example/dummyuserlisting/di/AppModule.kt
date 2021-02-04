package com.example.dummyuserlisting.di

import android.content.Context
import com.example.dummyuserlisting.data.local.AppDataBase
import com.example.dummyuserlisting.data.local.UserDao
import com.example.dummyuserlisting.data.remote.UserRemoteDataSource
import com.example.dummyuserlisting.data.remote.ApiService
import com.example.dummyuserlisting.data.repo.UserRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule{

    @Provides
    @Singleton
    fun providesRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://randomuser.me/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }


    @Singleton
    @Provides
    fun provideRemoteApi(apiService: ApiService) = UserRemoteDataSource(apiService)


    @Provides
    @Singleton
    fun provideEndPoints(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()


    @Singleton
    @Provides
    fun provideUserDao(db: AppDataBase) = db.userDao()

    @Singleton
    @Provides
    fun provideRepository(apiHelper: UserRemoteDataSource, userDao: UserDao) = UserRepository(apiHelper, userDao)


    @Provides
    @Singleton
    fun providesRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()


    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context) = AppDataBase.getDatabase(appContext)

}