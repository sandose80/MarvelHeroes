package com.costular.marvelheroes.data.db

import android.arch.persistence.room.*
import com.costular.marvelheroes.data.model.MarvelHero
import com.costular.marvelheroes.domain.model.MarvelHeroAttrEntity
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import io.reactivex.Maybe

/**
 * Created by jerosanchez on 30/6/2018
 */

@Dao
abstract class MarvelHeroesDao() {

    // get all cached heroes;
    // it returns a Maybe because it might return nothing
    // (i.e., before our first call to the API)
    @Query("SELECT * FROM heroes")
    abstract fun getAll(): Maybe<List<MarvelHero>>

    // refresh heroes cache from the API
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun update(heroesList: List<MarvelHeroEntity>)

    // check if we already have extended attributes
    // stored locally for an hero
    @Query("SELECT * FROM heroes_attr WHERE id = :id LIMIT 1")
    abstract fun findAttrsById(id: String): MarvelHeroAttrEntity?

    // add extended local attributes for a hero
    @Insert
    abstract fun insertLocalAttr(extendedAttrs: MarvelHeroAttrEntity)

    // update extended local attributes for a hero
    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun updateLocalAttr(extendedAttrs: MarvelHeroAttrEntity)

    // check favorite mark for a hero
    @Query("SELECT favorite FROM heroes_attr WHERE id = :id LIMIT 1")
    abstract fun isFavorite(id: String): Maybe<Boolean>

}