package com.costular.marvelheroes.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.costular.marvelheroes.data.model.MarvelHero
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import io.reactivex.Maybe

/**
 * Created by jerosanchez on 30/6/2018
 */

@Dao
abstract class MarvelHeroesDao() {

    // operation to get all stored heroes;
    // it returns a Maybe because it might return nothing
    // (i.e., before our first call to the API)

    @Query("SELECT * FROM heroes")
    abstract fun getAll(): Maybe<List<MarvelHero>>

    // operation to refresh heroes cache from the API

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun update(heroesList: List<MarvelHeroEntity>)

}