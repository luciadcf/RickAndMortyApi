package es.luciadcf.rickandmorty.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

fun ImageView.load(url: String?) {
    url?.let {
        Glide
            .with(context)
            .load(url)
            .timeout(30000)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
    }
}

fun ImageView.loadWithCenterCrop(url: String?) {
    url?.let {
        Glide
            .with(context)
            .load(url)
            .transform(CircleCrop())
            .timeout(30000)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
    }
}

