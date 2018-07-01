package com.costular.marvelheroes.data.db

import android.arch.persistence.room.TypeConverter

/**
 * Created by jerosanchez on 1/7/2018
 */

typealias MarvelHeroGroups = Array<String>

class MarvelHeroesTypeConverters {

    // type converters from complex to simple types,
    // so Room can handle table fields properly

    // --- HeroGroups

    companion object {
        @TypeConverter @JvmStatic
        fun fromHeroGroupsToString(marvelHeroeGroups: MarvelHeroGroups): String =
                marvelHeroeGroups.joinToString(", ")

        @TypeConverter @JvmStatic
        fun fromStringToHeroGroups(str: String): MarvelHeroGroups =
            str.replace("\\s".toRegex(), "").split(",").toTypedArray()
    }

    // --- other type converters here...

}