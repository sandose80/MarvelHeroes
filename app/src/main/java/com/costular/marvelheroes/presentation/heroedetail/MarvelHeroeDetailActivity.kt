package com.costular.marvelheroes.presentation.heroedetail

import android.graphics.drawable.Drawable
import android.opengl.Visibility
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
import kotlinx.android.synthetic.main.activity_hero_detail.*

/**
 * Created by costular on 18/03/2018.
 */
class MarvelHeroeDetailActivity : AppCompatActivity() {

    companion object {
        const val PARAM_HEROE = "heroe"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_detail)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
        supportPostponeEnterTransition() // Wait for image load and then draw the animation

        val hero: MarvelHeroEntity? = intent?.extras?.getParcelable(PARAM_HEROE)
        hero?.let { fillHeroData(it) }

        setUpStarListeners()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.heroe_detail, menu)
        return true
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            R.id.starHeroe -> {
                val newVisibility = noStarImage.visibility
                noStarImage.visibility = starImage.visibility
                starImage.visibility = newVisibility
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUpStarListeners() {

        // swap starImage visibility when tapped
        // as an alternative to menu option
        starImage.setOnClickListener {
            val newVisibility = noStarImage.visibility
            noStarImage.visibility = starImage.visibility
            starImage.visibility = newVisibility
            // TODO: persist status
        }

        // swap noStarImage visibility when tapped
        // as an alternative to menu option
        noStarImage.setOnClickListener {
            val newVisibility = starImage.visibility
            starImage.visibility = noStarImage.visibility
            noStarImage.visibility = newVisibility
            // TODO: persist status
        }

    }

}