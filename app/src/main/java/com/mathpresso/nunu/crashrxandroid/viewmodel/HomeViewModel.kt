package com.mathpresso.nunu.crashrxandroid.viewmodel

import com.mathpresso.nunu.crashrxandroid.datasource.TaskDataSource
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel : RxViewModel() {
    fun fetchAnything() {
        disposableScope.launch {
            Observable.fromIterable(TaskDataSource.getTasksList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {  }
        }
    }
}