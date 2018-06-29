package com.costular.marvelheroes.presentation.util.mvvm

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by jerosanchez on 29/6/2018
 */

abstract class BaseViewModel: ViewModel() {

    // kind of trash-bin to get rid of disposables
    // and avoid memory leaks when finished

    val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}