package com.costular.marvelheroes.domain.model

import android.annotation.SuppressLint
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.jetbrains.annotations.NotNull

/**
 * Created by costular on 17/03/2018.
 */

@Entity(tableName = "heroes")
@SuppressLint("ParcelCreator")
@Parcelize
data class MarvelHeroEntity(
        @NotNull
        @PrimaryKey
        val name: String,
        val photoUrl: String,
        val realName: String ,
        val height: String,
        val power: String,
        val abilities: String

        // TODO: use @TypeConverters to handle Array<String>
        // https://thetechguru.in/storing-java-objects-primitive-types-room-database/

//        val groups: Array<String>

) : Parcelable