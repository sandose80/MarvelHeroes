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
    fun provideMarvelHeroMapper(): MarvelHeroMapper = MarvelHeroMapper()

    @Provides
    @Singleton
    fun provideRemoteMarvelHeroesDataSoruce(marvelHeroesService: MarvelHeroesService)
            : RemoteMarvelHeroesDataSource =
                RemoteMarvelHeroesDataSource(marvelHeroesService)

    @Provides
    @Singleton
    fun provideMarvelHeroeDatabase(context: Context): MarvelHeroesDatabase =
            Room.databaseBuilder(context, MarvelHeroesDatabase::class.java, BuildConfig.DB_NAME)
                    .build()

    @Provides
    @Singleton
    fun provideLocalMarvelHeroesDataSource(marvelHeroesDatabase: MarvelHeroesDatabase)
            : LocalMarvelHeroesDataSource =
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