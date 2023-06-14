package es.luciadcf.rickandmorty.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import es.luciadcf.rickandmorty.ui.activity.MainActivity

abstract class BaseFragment<Any : ViewBinding> : Fragment() {

    private lateinit var viewBinding: Any

    abstract fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): Any

    fun getViewBinding() = viewBinding

    fun showLoader(loading: Boolean) {
        (activity as MainActivity).showLoading(loading)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewBinding = inflateViewBinding(inflater, container)
        return viewBinding.root
    }

}