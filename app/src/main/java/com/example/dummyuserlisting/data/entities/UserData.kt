package com.example.dummyuserlisting.data.entities


data class UserDataResponse(val results: List<User>)

data class User(val name: UserName, val email:String)

data class UserName(val first: String, val last: String)

