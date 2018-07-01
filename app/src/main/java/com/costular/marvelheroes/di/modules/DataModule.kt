package com.costular.marvelheroes.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.costular.marvelheroes.BuildConfig
import com.costular.marvelheroes.data.db.MarvelHeroesDatabase
import com.costular.marvelheroes.data.model.mapper.MarvelHeroMapper
import com.costular.marvelheroes.data.net.MarvelHeroesService
import com.costular.marvelheroes.data.repository.MarvelHeroesRepositoryImpl
import com.costular.marvelheroes.data.repository.datasource.LocalMarvelHeroesDataSource
import com.costular.marvelheroes.data.repository.datasource.RemoteMarvelHeroesDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by costular on 17/03/2018.
 */
@Module
class DataModule {

    @Provides
    @Singleton
    fun provideMarvelHeroMapper(
            // no dependency
    ): MarvelHeroMapper =
            MarvelHeroMapper()

    @Provides
    @Singleton
    fun provideRemoteMarvelHeroesDataSource(
            marvelHeroesService: MarvelHeroesService
    ): RemoteMarvelHeroesDataSource =
            RemoteMarvelHeroesDataSource(marvelHeroesService)

    @Provides
    @Singleton
    fun provideMarvelHeroesDatabase(
            context: Context
    ): MarvelHeroesDatabase =
            Room.databaseBuilder(context, MarvelHeroesDatabase::class.java, BuildConfig.DB_NAME)

                    // yes, I know, this SHOULD NOT BE USED in production
                    // TODO: refactor to do all database operations in background
                    .allowMainThreadQueries()

                    .build()


    @Provides
    @Singleton
    fun provideLocalMarvelHeroesDataSource(
            marvelHeroesDatabase: MarvelHeroesDatabase
    ): LocalMarvelHeroesDataSource =
            LocalMarvelHeroesDataSource(marvelHeroesDatabase)

    @Provides
    @Singleton
    fun provideMarvelHeroesRepository(
            remoteMarvelHeroesDataSource: RemoteMarvelHeroesDataSource,
            localMarvelHeroesDataSource: LocalMarvelHeroesDataSource,
            marvelHeroMapper: MarvelHeroMapper
    ): MarvelHeroesRepositoryImpl =
            MarvelHeroesRepositoryImpl (
                    remoteMarvelHeroesDataSource,
                    localMarvelHeroesDataSource,
                    marvelHeroMapper
            )

}