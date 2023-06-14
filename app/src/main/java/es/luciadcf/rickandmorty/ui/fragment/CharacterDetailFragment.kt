package es.luciadcf.rickandmorty.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import es.luciadcf.rickandmorty.R
import es.luciadcf.rickandmorty.constant.CharacterConstants
import es.luciadcf.rickandmorty.data.model.bo.CharacterBo
import es.luciadcf.rickandmorty.data.util.CustomError
import es.luciadcf.rickandmorty.data.util.ResourceObserver
import es.luciadcf.rickandmorty.databinding.FragmentCharacterDetailBinding
import es.luciadcf.rickandmorty.extension.load
import es.luciadcf.rickandmorty.extension.popBack
import es.luciadcf.rickandmorty.extension.showErrorDialog
import es.luciadcf.rickandmorty.ui.viewmodel.CharacterDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterDetailFragment : BaseFragment<FragmentCharacterDetailBinding>() {

    private val viewModel: CharacterDetailViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initClickListeners()
    }

    private fun initClickListeners() {
        getViewBinding().characterDetailImgBack.setOnClickListener { popBack() }
    }

    private fun initObservers() {
        initDetailObserver()
        initUpdateCharacterObserver()
    }

    private fun initDetailObserver() {
        viewModel.getCharacterDetailData()
            .observe(viewLifecycleOwner, object : ResourceObserver<CharacterBo>() {
                override fun onSuccess(response: CharacterBo?) {
                    response?.let {
                        initView(it)
                    }
                }

                override fun onError(error: CustomError) {
                    super.onError(error)
                    showErrorDialog(error.serverErrorMessage ?: getString(R.string.unknown_error))
                }

                override fun onLoading(loading: Boolean) {
                    super.onLoading(loading)
                    showLoader(loading)
                }
            })

        arguments?.getInt(CharacterConstants.ARG_CHARACTER_ID)?.let {
            viewModel.getCharacterDetail(it)
        }
    }

    private fun initUpdateCharacterObserver() {
        viewModel.getUpdateCharacterData()
            .observe(viewLifecycleOwner, object : ResourceObserver<Boolean>() {
                override fun onSuccess(response: Boolean?) {
                    if (response == true) {
                        Toast.makeText(context, "Character updated successfully", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                override fun onError(error: CustomError) {
                    super.onError(error)
                    showErrorDialog(error.serverErrorMessage ?: getString(R.string.unknown_error))
                }

                override fun onLoading(loading: Boolean) {
                    super.onLoading(loading)
                    getViewBinding().characterDetailBtnUpdate?.showLoading(loading)
                }
            })

        arguments?.getInt(CharacterConstants.ARG_CHARACTER_ID)?.let {
            viewModel.getCharacterDetail(it)
        }
    }

    private fun initView(character: CharacterBo) {
        getViewBinding().characterDetailImg.load(character.image)
        getViewBinding().characterDetailLabelName.text = character.name
        getViewBinding().characterDetailEdittextGender?.setText(character.gender)
        getViewBinding().characterDetailEdittextStatus?.setText(character.status)
        getViewBinding().characterDetailEdittextSpecies?.setText(character.species)
        getViewBinding().characterDetailEdittextType?.setText(character.type)
        getViewBinding().characterDetailEdittextOrigin?.setText(character.origin)
        getViewBinding().characterDetailEdittextLocation?.setText(character.location)

        getViewBinding().characterDetailBtnUpdate?.setOnClickListener {
            val characterUpdated = CharacterBo(
                character.id,
                character.name,
                character.image,
                getViewBinding().characterDetailEdittextStatus?.text,
                getViewBinding().characterDetailEdittextSpecies?.text,
                getViewBinding().characterDetailEdittextType?.text,
                getViewBinding().characterDetailEdittextGender?.text,
                getViewBinding().characterDetailEdittextOrigin?.text,
                getViewBinding().characterDetailEdittextLocation?.text
            )
            viewModel.updateCharacter(characterUpdated)
        }
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentCharacterDetailBinding {
        return FragmentCharacterDetailBinding.inflate(inflater, container, false)
    }

}