package es.luciadcf.rickandmorty

import android.app.Application
import es.luciadcf.rickandmorty.di.appModule
import es.luciadcf.rickandmorty.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RickAndMortyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeKoin()
    }

    private fun initializeKoin() {
        startKoin {
            androidContext(this@RickAndMortyApp)
            modules(
                listOf(
                    appModule,
                    dataModule
                )
            )
        }
    }
}