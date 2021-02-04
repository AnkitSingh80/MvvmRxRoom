package com.example.dummyuserlisting.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.dummyuserlisting.data.entities.UserProps
import com.example.dummyuserlisting.data.repo.UserRepository
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class UserListViewModel @ViewModelInject constructor(private val userRepo: UserRepository): ViewModel() {


    private val progressBarSubject = BehaviorSubject.create<Boolean>()
    private val userDataBehaviourSubject = BehaviorSubject.create<List<UserProps>>()

    fun observeUserData(): Observable<List<UserProps>> = userDataBehaviourSubject
    fun observeProgressBar(): Observable<Boolean> = progressBarSubject

    fun saveAcceptStatus(id:String) {
        userRepo.saveAcceptStatus(id)
    }

    fun saveDeclineStatus(id:String) {
        userRepo.saveDeclineStatus(id)
    }

    fun getUserData(): Disposable {
        return userRepo.getUsersList()
            .doOnSubscribe { progressBarSubject.onNext(true) }
            .doOnError {}
            .observeOn(AndroidSchedulers.mainThread())
            .firstElement()
            .subscribe { it -> setUserData(it) }

    }

    private fun setUserData(userProps: List<UserProps>) {
        progressBarSubject.onNext(false)
        userDataBehaviourSubject.onNext(userProps)
    }

    fun onClear() {
        onCleared()
    }


}
