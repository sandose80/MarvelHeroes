package com.costular.marvelheroes.presentation.heroeslist

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.costular.marvelheroes.R
import com.costular.marvelheroes.di.components.DaggerGetMarvelHeroesListComponent
import com.costular.marvelheroes.di.modules.GetMarvelHeroesListModule
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import com.costular.marvelheroes.presentation.MainApp
import com.costular.marvelheroes.presentation.servicelocator.Injector
import com.costular.marvelheroes.presentation.util.Navigator
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class HeroesListActivity: AppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    // TODO: make Dagger work with heroes list ViewModel

    // unable to make ModelView injections work... ´(
    // got the following error:
    // android.arch.lifecycle.View Model Provider.Factory cannot be provided without an @Provides

//    @Inject
//    lateinit var viewModelFactory: ViewModelProvider.Factory

    // using an Injector service locator instead... meanwhile
    private val heroesListViewModel = HeroesListViewModel(Injector.heroesRepository)

    private lateinit var adapter: HeroesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUp()
    }

    fun inject() {
        DaggerGetMarvelHeroesListComponent.builder()
                .applicationComponent((application as MainApp).component)
                .getMarvelHeroesListModule(GetMarvelHeroesListModule(this))
                .build()
                .inject(this)
    }

    private fun setUp() {
        setUpRecycler()
        bindEvents()
    }

    private fun bindEvents() {

        // changes in loading indicator state
        // we expect two possible values:
        // 1. true, before starting loading heroes list
        // 2. false, when heroes list is loaded
        heroesListViewModel
                .isLoadingIndicatorState
                .observe(this, Observer { isLoading ->
                    isLoading?.let {
                        showLoading(isLoading)
                    }
                })

        // changes in heroes list state
        // we expect only one event:
        // a new heroes list is ready to load in the recycler view
        heroesListViewModel
                .heroesListState
                .observe(this, Observer { heroesList ->
                    heroesList?.let {
                        showHeroesList(heroesList)
                    }
                })

    }

    override fun onResume() {
        super.onResume()

        // get the ball rolling...
        // load heroes list whenever the view gets the focus
        heroesListViewModel.loadHeroesList()
    }

    private fun setUpRecycler() {
        adapter = HeroesListAdapter { hero, image ->
            goToHeroDetail(hero, image)
        }
        heroesListRecycler.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        heroesListRecycler.itemAnimator = DefaultItemAnimator()
        heroesListRecycler.adapter = adapter
    }

    private fun goToHeroDetail(hero: MarvelHeroEntity, image: View) {
        navigator.goToHeroDetail(this, hero, image)
    }

    fun showLoading(isLoading: Boolean) {
        heroesListLoading.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    fun showHeroesList(heroes: List<MarvelHeroEntity>) {
        adapter.swapData(heroes)
    }

}
