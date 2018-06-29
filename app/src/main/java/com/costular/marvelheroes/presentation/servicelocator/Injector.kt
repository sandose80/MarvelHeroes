package com.costular.marvelheroes.presentation.servicelocator

import com.costular.marvelheroes.data.repository.MarvelHeroesRepositoryImpl

/**
 * Created by jerosanchez on 29/6/2018
 */

object Injector {

    lateinit var heroesRepository: MarvelHeroesRepositoryImpl

}