package es.luciadcf.rickandmorty.ui.view.loading_button

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import es.luciadcf.rickandmorty.R
import es.luciadcf.rickandmorty.databinding.ViewLoadingButtonBinding
import es.luciadcf.rickandmorty.extension.NO_STYLE
import es.luciadcf.rickandmorty.extension.getColor
import es.luciadcf.rickandmorty.extension.getStyleTypeArray

class LoadingButtonView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val viewBinding = ViewLoadingButtonBinding.inflate(
        LayoutInflater.from(context), this, true
    )
    private var currentText: String? = null

    init {
        showLoading(false)
        applyAttributes(attrs)
    }

    fun showLoading(show: Boolean) {
        if (show) {
            setText(null)
            viewBinding.loadingButtonProgressLoader.visibility = View.VISIBLE
        } else {
            setText(currentText)
            viewBinding.loadingButtonProgressLoader.visibility = View.GONE
        }
        viewBinding.loadingButtonBtnAction.isClickable = !show
    }

    fun setOnClickListener(clickListener: (View) -> Unit) {
        viewBinding.loadingButtonBtnAction.setOnClickListener(clickListener)
    }

    private fun applyAttributes(attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = getStyleTypeArray(attrs, R.styleable.LoadingButtonView)
            currentText = typedArray.getString(R.styleable.LoadingButtonView_btn_text)
            val textColor =
                typedArray.getColor(R.styleable.LoadingButtonView_btn_text_color, Color.BLACK)
            val backgroundColor = typedArray.getColor(
                R.styleable.LoadingButtonView_btn_background_color,
                getColor(android.R.color.transparent)
            )
            val drawable = typedArray.getDrawable(
                R.styleable.LoadingButtonView_btn_background_drawable
            )
            val textStyle = typedArray.getResourceId(
                R.styleable.LoadingButtonView_btn_text_style, NO_STYLE
            )
            if (textStyle != NO_STYLE) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    viewBinding.loadingButtonBtnAction.setTextAppearance(textStyle)
                } else {
                    viewBinding.loadingButtonBtnAction.setTextAppearance(context, textStyle)
                }
            }
            val hasRadius =
                typedArray.getBoolean(R.styleable.LoadingButtonView_btn_has_radius, true)

            if (!hasRadius) {
                viewBinding.root.radius = 0f
            }

            viewBinding.loadingButtonBtnAction.setTextColor(textColor)
            setCustomBackgroundColor(backgroundColor)
            drawable?.let {
                viewBinding.loadingButtonBtnAction.background = it
            }

            setText(currentText)
        }
    }

    fun getText(): CharSequence = viewBinding.loadingButtonBtnAction.text

    fun setText(text: String?) {
        viewBinding.loadingButtonBtnAction.text = text
    }

    fun setCustomBackgroundColor(backgroundColor: Int) {
        viewBinding.loadingButtonBtnAction.setBackgroundColor(backgroundColor)
    }


}
