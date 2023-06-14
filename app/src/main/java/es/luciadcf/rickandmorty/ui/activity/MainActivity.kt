package es.luciadcf.rickandmorty.ui.activity

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import es.luciadcf.rickandmorty.R
import es.luciadcf.rickandmorty.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main__container__nav_host) as NavHostFragment
        navController = navHostFragment.navController
    }

    fun showLoading(loading: Boolean) {
        if (loading) {
            viewBinding.fragmentContainer.visibility = GONE
            viewBinding.mainContainerLoader.visibility = VISIBLE
        } else {
            viewBinding.fragmentContainer.visibility = VISIBLE
            viewBinding.mainContainerLoader.visibility = GONE
        }
    }
}