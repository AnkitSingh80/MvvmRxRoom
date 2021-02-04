package com.example.dummyuserlisting.data.remote

import com.example.dummyuserlisting.data.entities.UserDataResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService{

    @GET("api/?results=10")
    fun getData(): Observable<UserDataResponse>
}