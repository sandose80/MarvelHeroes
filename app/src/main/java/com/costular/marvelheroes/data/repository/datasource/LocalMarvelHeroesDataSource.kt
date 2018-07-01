package com.costular.marvelheroes.data.repository.datasource

import com.costular.marvelheroes.data.db.MarvelHeroesDatabase
import com.costular.marvelheroes.data.model.MarvelHero
import com.costular.marvelheroes.domain.model.MarvelHeroAttrEntity
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

    // methods to support features like caching
    // and extended attributes (i.e. the favorite mark)
    // that are handled locally

    fun refreshCacheFrom(heroesList: List<MarvelHeroEntity>) =
        marvelHeroesDatabase
                .getMarvelHeroesDao()
                .update(heroesList)

    fun setFavorite(hero:MarvelHeroEntity, isFavorite: Boolean) =
        marvelHeroesDatabase.runInTransaction {

            // get a reference to the DAO for convenience
            val marvelHeroesDao = marvelHeroesDatabase.getMarvelHeroesDao()

            // check if the hero already has extended attrs stored locally
            val existingAttrs = marvelHeroesDao.findAttrsById(hero.name)

            if (existingAttrs == null) {
                // there's no extended attrs yet, insert
                val newAttrs = MarvelHeroAttrEntity(hero.name, isFavorite)
                marvelHeroesDao.insertLocalAttr(newAttrs)
            } else {
                // there're already extended attrs, update
                val newAttrs = MarvelHeroAttrEntity(existingAttrs.id, isFavorite)
                marvelHeroesDao.updateLocalAttr(newAttrs)
            }

        }

    fun isFavorite(hero:MarvelHeroEntity): Observable<Boolean> =
            marvelHeroesDatabase
                    .getMarvelHeroesDao()
                    .isFavorite(hero.name)
                    .toObservable()

}