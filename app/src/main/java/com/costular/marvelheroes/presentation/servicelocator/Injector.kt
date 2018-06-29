package com.costular.marvelheroes.presentation.servicelocator

import com.costular.marvelheroes.data.repository.MarvelHeroesRepositoryImpl

/**
 * Created by jerosanchez on 29/6/2018
 */

// too bad, I know, but I've been unable to make ViewModel injections work yet

object Injector {

    lateinit var heroesRepository: MarvelHeroesRepositoryImpl

}