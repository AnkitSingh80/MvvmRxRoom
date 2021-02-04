package com.example.dummyuserlisting.data.repo

import com.example.dummyuserlisting.data.local.UserDao
import com.example.dummyuserlisting.data.entities.UserProps
import com.example.dummyuserlisting.data.remote.UserRemoteDataSource
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserRepository @Inject constructor(private val remoteDataSource: UserRemoteDataSource,
                                         private val localDataSource: UserDao) {



    fun getUsersList(): Observable<List<UserProps>> {
        return Observable.concat(fetchFromNetwork(), loadFromDb()).lastElement().toObservable()
    }

    fun saveAcceptStatus(id: String) {
        localDataSource.updateUserAcceptStatus(id,true)
    }

    fun saveDeclineStatus(id: String) {
        localDataSource.updateUserDeclineStatus(id,true)
    }


    private fun fetchFromNetwork(): Observable<List<UserProps>> {
        return remoteDataSource.getUsers()
            .subscribeOn(Schedulers.io())
            .map { it ->
                it.results.map {
                    UserProps(it.email,false, false)
                }
            }.doOnNext {
                insertList(it)
            }
    }

    private fun loadFromDb(): Observable<List<UserProps>> {
      return localDataSource.getUserList()
    }

    private fun insertList(userProps: List<UserProps>) {
        localDataSource.insertAllUsers(userProps)
    }

}

