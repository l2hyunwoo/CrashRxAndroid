package com.mathpresso.nunu.crashrxandroid

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mathpresso.nunu.crashrxandroid.datasource.TaskDataSource
import com.mathpresso.nunu.crashrxandroid.model.Task
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.AsyncSubject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val taskObservable = Observable
            .fromIterable(TaskDataSource.getTasksList())
            .subscribeOn(Schedulers.io())
            // filter 작업은 subscribe 작업 -> Background Thread에서 처리 됨
            .filter {
                Thread.sleep(2000L)
                it.isComplete
            }
            .observeOn(AndroidSchedulers.mainThread())

        // create 함수는 Cold Observable을 내놓는다
        val taskObservableCreate = Observable
            .create { emitter: ObservableEmitter<Task> ->
                emitter.onNext(Task(description = "", isComplete = false, priority = 0))
                // 끝내려면 onComplete 호출
                emitter.onComplete()
            }
            .subscribe { Log.d(TAG, "$it") }

        taskObservable.subscribe(object : Observer<Task> {
            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "onSubscribe called")
            }

            override fun onNext(t: Task) {
                Log.d(TAG, "onNext ${Thread.currentThread().name}")
                Log.d(TAG, "onNext : Task - ${t.description}")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError called")
            }

            override fun onComplete() {
                Log.d(TAG, "onComplete called")
            }
        })
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}