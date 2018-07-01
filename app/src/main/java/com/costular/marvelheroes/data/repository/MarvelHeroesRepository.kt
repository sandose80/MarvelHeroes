package com.costular.marvelheroes.data.repository

import com.costular.marvelheroes.domain.model.MarvelHeroAttrEntity
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import io.reactivex.Observable

/**
 * Created by costular on 17/03/2018.
 */
interface MarvelHeroesRepository {

    fun getMarvelHeroesList(): Observable<List<MarvelHeroEntity>>

    fun starHero(marvelHero: MarvelHeroEntity)

    fun unstarHero(marvelHero: MarvelHeroEntity)

    fun getHeroFavoriteStatus(marvelHero: MarvelHeroEntity): Observable<Boolean>
}