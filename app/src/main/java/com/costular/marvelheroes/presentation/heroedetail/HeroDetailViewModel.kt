package com.costular.marvelheroes.presentation.heroedetail

import android.arch.lifecycle.MutableLiveData
import com.costular.marvelheroes.data.repository.MarvelHeroesRepositoryImpl
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import com.costular.marvelheroes.presentation.util.mvvm.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Created by jerosanchez on 1/7/2018
 */

class HeroDetailViewModel (
        private val heroesRepository: MarvelHeroesRepositoryImpl
): BaseViewModel() {

    // --- outbound (model to view):

    val favoriteState: MutableLiveData<Boolean> = MutableLiveData()

    fun loadHeroFavoriteStatus(marvelHero: MarvelHeroEntity) {

        heroesRepository
                // delegate the loading to the repository
                .getHeroFavoriteStatus(marvelHero)

                // run the loading in background
                .subscribeOn(Schedulers.io())

                // handle values in the main thread so we can
                // use views if needed
                .observeOn(AndroidSchedulers.mainThread())

                // process "events" from the repository
                .subscribeBy(
                        onNext = { status ->
                            favoriteState.value = status
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

    // --- inbound (view to model):

    fun starHero(marvelHero: MarvelHeroEntity) {
        heroesRepository
                .starHero(marvelHero)
    }

    fun unstarHero(marvelHero: MarvelHeroEntity) {
        heroesRepository
                .unstarHero(marvelHero)
    }

}