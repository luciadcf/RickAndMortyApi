package es.luciadcf.rickandmorty.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.luciadcf.rickandmorty.R
import es.luciadcf.rickandmorty.constant.CharacterConstants
import es.luciadcf.rickandmorty.data.model.bo.CharacterBo
import es.luciadcf.rickandmorty.data.util.CustomError
import es.luciadcf.rickandmorty.data.util.ResourceObserver
import es.luciadcf.rickandmorty.databinding.FragmentCharacterListBinding
import es.luciadcf.rickandmorty.extension.showErrorDialog
import es.luciadcf.rickandmorty.ui.adapter.CharacterAdapter
import es.luciadcf.rickandmorty.ui.adapter.ItemClickListener
import es.luciadcf.rickandmorty.ui.viewmodel.CharactersViewModel
import es.luciadcf.rickandmorty.util.SwipeToDeleteCallback
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.ceil

class CharacterListFragment : BaseFragment<FragmentCharacterListBinding>() {

    private val charactersViewModel: CharactersViewModel by viewModel()
    private var charactersAdapter: CharacterAdapter? = null
    private var page: Int = 1
    private var isLoading = false
    private var isLoadFinished = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        initScrollListener()
        initObservers()
        enableSwipeToDelete()
    }

    private fun setupList() {
        getViewBinding().characterListData.apply {
            if (charactersAdapter == null) {
                charactersAdapter = CharacterAdapter(object : ItemClickListener {
                    override fun invoke(id: Int) {
                        findNavController().navigate(
                            R.id.action_list_to_detail,
                            bundleOf(Pair(CharacterConstants.ARG_CHARACTER_ID, id))
                        )
                    }
                })
            }
            charactersAdapter?.let {
                adapter = it
            }
        }
    }

    private fun initScrollListener() {
        getViewBinding().characterListData.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                (getViewBinding().characterListData.layoutManager as? LinearLayoutManager)?.let {
                    val totalItemCount = it.itemCount
                    val lastVisibleItem = it.findLastVisibleItemPosition()
                    val isDownScroll = dy > 0
                    val isReady = !isLoading && isLoadFinished
                    val isInRange = totalItemCount <= (lastVisibleItem + 5)
                    if (isDownScroll && isInRange && isReady) {
                        charactersAdapter?.let { adapter ->
                            page =
                                (ceil((adapter.getData().lastIndex.toDouble() / 20.0)) + 1).toInt()
                            charactersViewModel.getCharacters(page, true)
                            isLoadFinished = false
                        }
                    }
                }
            }
        })
    }

    private fun enableSwipeToDelete() {
        context?.let {
            val swipeToDeleteCallback = object : SwipeToDeleteCallback(it) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
                    val position = viewHolder.adapterPosition
                    val item = charactersAdapter?.getData()?.get(position)
                    charactersAdapter?.removeItem(position)
                    item?.let {
                        charactersViewModel.removeCharacter(item)
                    }
                }
            }

            ItemTouchHelper(swipeToDeleteCallback).apply {
                attachToRecyclerView(getViewBinding().characterListData)
            }
        }
    }

    //region Observers
    private fun initObservers() {
        initCharactersObserver()
        initRemoveCharacterObserver()
    }

    private fun initCharactersObserver() {
        charactersViewModel.getCharactersData()
            .observe(viewLifecycleOwner, object : ResourceObserver<List<CharacterBo>>() {
                override fun onSuccess(response: List<CharacterBo>?) {
                    response?.let {
                        getViewBinding().characterListData.apply {
                            if (charactersAdapter?.getData()?.size == 0) {
                                adapter = charactersAdapter?.apply {
                                    setData(it)
                                }
                            } else {
                                charactersAdapter?.addData(it)
                            }
                        }
                        isLoadFinished = true
                    } ?: run {
                        onError(CustomError())
                    }
                }

                override fun onError(error: CustomError) {
                    super.onError(error)
                    showErrorDialog()
                }

                override fun onLoading(loading: Boolean) {
                    super.onLoading(loading)
                    isLoading = loading
                    isLoadFinished = !loading
                    showLoader(loading)
                }
            })

        val sharedPref = activity?.getSharedPreferences("APP", Context.MODE_PRIVATE)
        val isFirstTime = sharedPref?.getBoolean("FIRST_TIME", true) ?: true
        charactersViewModel.getCharacters(isRemote = isFirstTime)

        sharedPref?.edit()?.apply {
            putBoolean("FIRST_TIME", false)
            apply()
        }
    }

    private fun initRemoveCharacterObserver() {
        charactersViewModel.getRemoveCharacterData()
            .observe(viewLifecycleOwner, object : ResourceObserver<Boolean>() {
                override fun onSuccess(response: Boolean?) {
                    if (response == true) {
                        Toast.makeText(
                            context, "Item was removed from the list.", Toast.LENGTH_LONG
                        ).show()
                    }
                }
            })
    }

    //endregion

    override fun inflateViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentCharacterListBinding {
        return FragmentCharacterListBinding.inflate(inflater, container, false)
    }

}