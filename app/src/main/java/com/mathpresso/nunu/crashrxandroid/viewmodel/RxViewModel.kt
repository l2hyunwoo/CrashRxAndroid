package com.mathpresso.nunu.crashrxandroid.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class RxViewModel : ViewModel() {
    protected val disposableScope = CompositeDisposable()

    fun CompositeDisposable.launch(block: () -> Disposable) {
        disposableScope.add(block.invoke())
    }

    override fun onCleared() {
        super.onCleared()
        disposableScope.clear()
    }
}