package com.costular.marvelheroes.presentation.heroedetail

import android.arch.lifecycle.Observer
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.costular.marvelheroes.R
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import com.costular.marvelheroes.presentation.servicelocator.Injector
import kotlinx.android.synthetic.main.activity_hero_detail.*

/**
 * Created by costular on 18/03/2018.
 */
class HeroDetailActivity : AppCompatActivity() {

    companion object {
        const val PARAM_HEROE = "heroe"
    }

    // unable to make ModelView injections work... ´(
    // got the following error:
    // android.arch.lifecycle.View Model Provider.Factory cannot be provided without an @Provides

//    @Inject
//    lateinit var viewModelFactory: ViewModelProvider.Factory

    // using an Injector service locator instead... meanwhile
    private val heroDetailViewModel = HeroDetailViewModel(Injector.heroesRepository)

    // the hero details we are displaying:
    private var hero: MarvelHeroEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_detail)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
        supportPostponeEnterTransition() // Wait for image load and then draw the animation

        setUp()
    }

    private fun setUp() {
        hero = intent?.extras?.getParcelable(PARAM_HEROE)
        hero?.let {
            fillHeroData(it)
        }

        setUpStarImageListeners()
        bindEvents()
    }

    private fun fillHeroData(hero: MarvelHeroEntity) {
        Glide.with(this)
                .load(hero.photoUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }
                })
                .into(heroDetailImage)

        heroDetailName.text = hero.name
        heroDetailRealName.text = hero.realName
        heroDetailHeight.text = hero.height
        heroDetailPower.text = hero.power
        heroDetailAbilities.text = hero.abilities
    }

    private fun setUpStarImageListeners() {

        // swap starImage visibility and unstar hero when tapped
        // as an alternative to menu option
        starImage.setOnClickListener {
            unstarHero()
        }

        // swap noStarImage visibility and star hero when tapped
        // as an alternative to menu option
        noStarImage.setOnClickListener {
            starHero()
        }

    }

    private fun bindEvents() {

        // observe hero favorite state and
        // update view accordingly
        heroDetailViewModel
                .favoriteState
                .observe(this, Observer { status ->
                    status?.let { isStarred ->
                        if (isStarred) {
                            starImage.visibility = View.VISIBLE
                            noStarImage.visibility = View.GONE
                        } else {
                            starImage.visibility = View.GONE
                            noStarImage.visibility = View.VISIBLE
                        }
                    }

                })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.heroe_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            R.id.starHero -> {

                // star/unstar hero as required
                hero?.let {
                    if (starImage.visibility == View.VISIBLE) {
                        unstarHero()
                    } else{
                        starHero()
                    }
                }
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()

        // get the ball rolling...
        // load hero favorite status whenever the view gets the focus
        hero?.let {
            heroDetailViewModel.loadHeroFavoriteStatus(it)
        }
    }

    private fun starHero() {
        starImage.visibility = View.VISIBLE
        noStarImage.visibility = View.GONE
        hero?.let {
            heroDetailViewModel.starHero(it)
        }
    }

    private fun unstarHero() {
        starImage.visibility = View.GONE
        noStarImage.visibility = View.VISIBLE
        hero?.let {
            heroDetailViewModel.unstarHero(it)
        }
    }

}