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

// local part of the Hero entity to handle
// extended attributes not belonging to the API,
// i.e. favorite status

@Entity(tableName = "heroes_attr")
@SuppressLint("ParcelCreator")
@Parcelize
data class MarvelHeroAttrEntity(
        @NotNull
        @PrimaryKey
        val id: String,
        val favorite: Boolean
) : Parcelable