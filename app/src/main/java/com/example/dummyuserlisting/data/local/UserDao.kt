package com.example.dummyuserlisting.data.local

import androidx.room.*
import com.example.dummyuserlisting.data.entities.UserProps
import io.reactivex.Observable

@Dao
interface UserDao{

    @Query("SELECT * FROM user_table")
    fun getUserList(): Observable<List<UserProps>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userProps: UserProps)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllUsers(userProps: List<UserProps>)


    @Query("UPDATE user_table SET user_accept = :accept  WHERE user_email = :id")
    fun updateUserAcceptStatus(id:String, accept:Boolean)

    @Query("UPDATE user_table SET user_reject = :reject WHERE user_email = :id")
    fun updateUserDeclineStatus(id:String, reject:Boolean)
}