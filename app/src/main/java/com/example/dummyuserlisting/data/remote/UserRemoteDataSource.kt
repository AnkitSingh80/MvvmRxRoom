package com.example.dummyuserlisting.data.remote

import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(private val apiService: ApiService){

    fun getUsers() = apiService.getData()

}