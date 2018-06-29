package com.costular.marvelheroes.presentation.heroeslist

import android.arch.lifecycle.MutableLiveData
import com.costular.marvelheroes.data.repository.MarvelHeroesRepositoryImpl
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import com.costular.marvelheroes.presentation.util.mvvm.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by jerosanchez on 29/6/2018
 */

class HeroesListViewModel @Inject constructor(
        val heroesRepository: MarvelHeroesRepositoryImpl
): BaseViewModel() {

    val heroesListState: MutableLiveData<List<MarvelHeroEntity>> = MutableLiveData()

    fun loadHeroesList() {

        heroesRepository

                // delegate the loading to the repository
                .getMarvelHeroesList()

                // run the loading in background
                .subscribeOn(Schedulers.io())

                // handle values in the main thread so we can
                // do stuff with views if needed
                .observeOn(AndroidSchedulers.mainThread())

                // process "events" from the repository
                .subscribeBy(
                    onNext = { listOfHeroesEntities ->
                        // change heroes list state, giving chance
                        // who is observing to do its stuff
                        heroesListState.value = listOfHeroesEntities
                    },
                    onComplete = {
                        // nothing to do
                        },
                    onError = {
                        // nothing to do
                        // defined just in case, so the app doesn't crash
                    }
                )

                // do not forget to clean up when finished
                .addTo(compositeDisposable)
    }
}