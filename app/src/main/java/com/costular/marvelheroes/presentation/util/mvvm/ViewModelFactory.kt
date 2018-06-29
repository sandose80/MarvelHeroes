package com.costular.marvelheroes.presentation.util.mvvm

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.costular.marvelheroes.presentation.heroeslist.HeroesListViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Singleton
import javax.inject.Provider
import kotlin.reflect.KClass

/**
 * Created by jerosanchez on 29/6/2018
 */

// a trick to allow Dagger to inject dependencies into ViewModel objects

@Singleton
class ViewModelFactory @Inject constructor(
        private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>)
: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return viewModels[modelClass]?.get() as T
    }
}

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {

    @Binds // <<< sames as @Provides, but no need to implement the method
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HeroesListViewModel::class)
    internal abstract fun postHeroesListViewModel(viewModel: HeroesListViewModel): ViewModel

    // Add more ViewModels here as needed...

//    @Binds
//    @IntoMap
//    @ViewModelKey(anotherViewModel::class)
//    internal abstract fun postAnotherViewModel(viewModel: anotherViewModel): ViewModel

}