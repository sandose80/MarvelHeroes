package com.costular.marvelheroes.domain.model

import android.annotation.SuppressLint
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.jetbrains.annotations.NotNull

/**
 * Created by jerosanchez on 1/7/2018
 */

@Entity(tableName = "heroes_attr")
@SuppressLint("ParcelCreator")
@Parcelize
data class MarvelHeroAttrEntity(
        @NotNull
        @PrimaryKey
        val id: String,
        val favorite: Boolean
) : Parcelable