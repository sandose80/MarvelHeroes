package com.costular.marvelheroes.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.costular.marvelheroes.domain.model.MarvelHeroAttrEntity
import com.costular.marvelheroes.domain.model.MarvelHeroEntity

/**
 * Created by jerosanchez on 30/6/2018
 */

@Database(
        entities = [
    MarvelHeroEntity::class,
    MarvelHeroAttrEntity::class],
        version = 1)
@TypeConverters(MarvelHeroesTypeConverters::class)
abstract class MarvelHeroesDatabase: RoomDatabase() {

    abstract fun getMarvelHeroesDao(): MarvelHeroesDao

}