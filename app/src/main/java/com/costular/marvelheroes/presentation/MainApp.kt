package com.costular.marvelheroes.presentation

import android.app.Application
import com.costular.marvelheroes.di.components.ApplicationComponent
import com.costular.marvelheroes.di.components.DaggerApplicationComponent
import com.costular.marvelheroes.di.modules.ApplicationModule
import com.costular.marvelheroes.presentation.servicelocator.Injector
import com.facebook.stetho.Stetho

/**
 * Created by costular on 16/03/2018.
 */
class MainApp : Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()

        // start debug bridge for Chrome Developer Tools
        // browse to this URL to start: chrome://inspect

        Stetho.initializeWithDefaults(this)

        // make some stuff available via an Injector service locator
        // still not able to make Dagger to inject dependencies into ViewModels...

        Injector.heroesRepository = component.getRepository()
    }

}