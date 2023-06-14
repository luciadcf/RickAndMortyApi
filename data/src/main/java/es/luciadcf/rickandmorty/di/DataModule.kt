package es.luciadcf.rickandmorty.di

import es.luciadcf.rickandmorty.data.BuildConfig
import es.luciadcf.rickandmorty.data.database.RickAndMortyDatabase
import es.luciadcf.rickandmorty.data.datasource.CharacterLocalDatasource
import es.luciadcf.rickandmorty.data.datasource.CharacterLocalDatasourceImpl
import es.luciadcf.rickandmorty.data.datasource.CharacterRemoteDatasource
import es.luciadcf.rickandmorty.data.datasource.CharacterRemoteDatasourceImpl
import es.luciadcf.rickandmorty.data.repository.CharacterRepository
import es.luciadcf.rickandmorty.data.repository.CharacterRepositoryImpl
import es.luciadcf.rickandmorty.data.ws.CharacterWs
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single {
        val interceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    single {
        Retrofit.Builder().client(get()).baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    single<CharacterRepository> { CharacterRepositoryImpl(get(), get()) }

    factory<CharacterRemoteDatasource> {
        CharacterRemoteDatasourceImpl(get())
    }

    factory {
        get<Retrofit>().create(CharacterWs::class.java)
    }

    // Database

    single {
        RickAndMortyDatabase.buildDatabase(get())
    }

    //region Daos
    factory {
        get<RickAndMortyDatabase>().getCharacterDao()
    }
    //endregion

    //region Datasources
    factory<CharacterLocalDatasource> {
        CharacterLocalDatasourceImpl(get())
    }
    //endregion

}