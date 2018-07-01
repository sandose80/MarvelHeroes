package com.costular.marvelheroes.data.repository

import com.costular.marvelheroes.data.model.mapper.MarvelHeroMapper
import com.costular.marvelheroes.data.repository.datasource.FakeMarvelHeroesDataSource
import com.costular.marvelheroes.data.repository.datasource.LocalMarvelHeroesDataSource
import com.costular.marvelheroes.data.repository.datasource.RemoteMarvelHeroesDataSource
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import io.reactivex.Observable

/**
 * Created by costular on 17/03/2018.
 */
class MarvelHeroesRepositoryImpl(
        private val remoteMarvelHeroesDataSource: RemoteMarvelHeroesDataSource,
        private val localMarvelHeroesDataSource: LocalMarvelHeroesDataSource,
        private val marvelHeroesMapper: MarvelHeroMapper
) : MarvelHeroesRepository {

    override fun getMarvelHeroesList(): Observable<List<MarvelHeroEntity>> =
        // first, return cached heroes list from our local database
        getMarvelHeroesFromDb()
                // then, as a second observed value,
                // return the newest list available from the API
                .concatWith(getMarvelHeroesFromApi())

    private fun getMarvelHeroesFromDb(): Observable<List<MarvelHeroEntity>> =
        localMarvelHeroesDataSource
                .getMarvelHeroesList()
                .map { heroesList ->
                    marvelHeroesMapper.transformList(heroesList)
                }

    private fun getMarvelHeroesFromApi(): Observable<List<MarvelHeroEntity>> =
        remoteMarvelHeroesDataSource
                .getMarvelHeroesList()
                .map { heroesList ->
                    marvelHeroesMapper.transformList(heroesList)
                }
                .doOnNext { heroesList ->
                    // cache latest data from the API into our local database,
                    // so we can deliver it quickly next time
                    localMarvelHeroesDataSource.refreshCacheFrom(heroesList)
                }

    // things that only happen locally;
    // delegate operations to the data source

    override fun starHero(marvelHeroe: MarvelHeroEntity) =
        localMarvelHeroesDataSource
                .setFavorite(marvelHeroe, true)

    override fun unstarHero(marvelHeroe: MarvelHeroEntity) =
        localMarvelHeroesDataSource
                .setFavorite(marvelHeroe, false)

    override fun getHeroFavoriteStatus(marvelHeroe: MarvelHeroEntity): Observable<Boolean> =
        localMarvelHeroesDataSource
                .isFavorite(marvelHeroe)

                // if there's no favorite mark locally stored yet
                // return false, i.e. hero is not a favorite
                .switchIfEmpty {
                    Observable.just(false)
                }

}