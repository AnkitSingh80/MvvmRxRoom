package com.example.dummyuserlisting.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dummyuserlisting.R
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class UserListActivity : AppCompatActivity(), UserListAdapter.OnItemClickListener {

    private val userListViewModel: UserListViewModel by viewModels()
    private lateinit var userListAdapter : UserListAdapter
    private val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setAdapter()
        requestData()
        observeProgressBar()
        observeUsersData()
    }

    private fun requestData() {
        compositeDisposable.add(userListViewModel.getUserData())
    }

    private fun setAdapter() {
        userListAdapter = UserListAdapter()
        rvUserList.adapter = userListAdapter
        rvUserList.layoutManager = LinearLayoutManager(this)
        userListAdapter.setOnItemClickListener(this)
    }

    private fun observeProgressBar() {
        compositeDisposable.add(userListViewModel.observeProgressBar()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it) {
                    showProgressBar()
                } else
                    hideProgressBar()
            })
    }

    private fun observeUsersData() {
        compositeDisposable.add(userListViewModel.observeUserData()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                userListAdapter.submitList(it)
                progressBar.visibility = View.GONE
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        userListViewModel.onClear()
        if(compositeDisposable.isDisposed.not())
        compositeDisposable.dispose()
    }

    override fun onAcceptClick(id:String) {
        userListViewModel.saveAcceptStatus(id)
    }

    override fun onDeclineClick(id:String) {
        userListViewModel.saveDeclineStatus(id)
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }


}