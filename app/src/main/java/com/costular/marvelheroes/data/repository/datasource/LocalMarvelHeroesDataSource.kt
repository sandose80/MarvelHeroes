package com.costular.marvelheroes.data.repository.datasource

import com.costular.marvelheroes.data.db.MarvelHeroesDatabase
import com.costular.marvelheroes.data.model.MarvelHero
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import io.reactivex.Observable

/**
 * Created by jerosanchez on 30/6/2018
 */

class LocalMarvelHeroesDataSource(
        private val marvelHeroesDatabase: MarvelHeroesDatabase
): MarvelHeroesDataSource {

    override fun getMarvelHeroesList(): Observable<List<MarvelHero>> =
        marvelHeroesDatabase
                .getMarvelHeroesDao()
                .getAll()
                .toObservable()

    fun refreshCacheFrom(heroesList: List<MarvelHeroEntity>) {
        marvelHeroesDatabase
                .getMarvelHeroesDao()
                .update(heroesList)
    }
}