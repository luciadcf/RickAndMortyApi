package es.luciadcf.rickandmorty.di

import es.luciadcf.rickandmorty.ui.domain.GetCharacterDetailUseCase
import es.luciadcf.rickandmorty.ui.domain.GetCharactersUseCase
import es.luciadcf.rickandmorty.ui.domain.RemoveCharacterUseCase
import es.luciadcf.rickandmorty.ui.domain.UpdateCharacterUseCase
import es.luciadcf.rickandmorty.ui.viewmodel.CharacterDetailViewModel
import es.luciadcf.rickandmorty.ui.viewmodel.CharactersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        CharactersViewModel(get(), get())
    }

    viewModel {
        CharacterDetailViewModel(get(), get())
    }

    factory {
        GetCharactersUseCase(get())
    }

    factory {
        GetCharacterDetailUseCase(get())
    }

    factory {
        RemoveCharacterUseCase(get())
    }

    factory {
        UpdateCharacterUseCase(get())
    }

}