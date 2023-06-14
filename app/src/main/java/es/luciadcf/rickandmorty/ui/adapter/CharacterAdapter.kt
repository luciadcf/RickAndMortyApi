package es.luciadcf.rickandmorty.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import es.luciadcf.rickandmorty.R
import es.luciadcf.rickandmorty.data.model.bo.CharacterBo
import es.luciadcf.rickandmorty.databinding.RowCharacterBinding
import es.luciadcf.rickandmorty.extension.loadWithCenterCrop


class CharacterAdapter(private val clickListener: ItemClickListener) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    private val list: MutableList<CharacterBo> = mutableListOf()

    fun getData() = list

    fun setData(data: List<CharacterBo>?) {
        data?.let {
            list.clear()
            list.addAll(it)
            notifyItemRangeChanged(0, list.size)
        }
    }

    fun addData(data: List<CharacterBo>?) {
        data?.let {
            list.addAll(it)
            notifyItemRangeChanged(list.size - 1, data.size)
        }
    }

    fun removeItem(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            RowCharacterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.setup(list[position], clickListener)
    }

    override fun getItemCount() = list.size

    class CharacterViewHolder(private val itemViewBinding: RowCharacterBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root) {

        fun setup(item: CharacterBo, clickListener: ItemClickListener) {
            itemViewBinding.root.animation =
                AnimationUtils.loadAnimation(itemViewBinding.root.context, R.anim.scale)
            itemViewBinding.rowCharacterLabelName.text =
                item.id.toString().plus(" ").plus(item.name)
            itemViewBinding.rowCharacterLabelSpecies.text = item.species
            item.image?.let {
                itemViewBinding.rowCharacterImg.loadWithCenterCrop(it)
            }
            itemViewBinding.root.setOnClickListener { clickListener.invoke(item.id) }
        }
    }

}

typealias ItemClickListener = (id: Int) -> Unit