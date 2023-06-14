package es.luciadcf.rickandmorty.ui.view.input

import android.content.Context
import android.graphics.Typeface
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import es.luciadcf.rickandmorty.R
import es.luciadcf.rickandmorty.databinding.ViewEditableTextBinding
import es.luciadcf.rickandmorty.extension.getStyleTypeArray

class EditableTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    companion object

    val text get() = viewBinding.editableTextInputInfo.text.toString().trim()
    var listener = object : SimpleEditableTextChangeListener {
        override fun onTextChanged(text: String) {
            // no-op
        }
    }

    var viewBinding = ViewEditableTextBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        applyAttributes(attrs)
        initWatcher()
    }

    fun setText(text: String?) {
        viewBinding.editableTextInputInfo.setText(text)
    }

    private fun setInputType(inputType: Int) {
        viewBinding.editableTextInputInfo.inputType = when (inputType) {
            CommonInputType.NONE -> InputType.TYPE_NULL
            CommonInputType.TEXT -> InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
            CommonInputType.PASSWORD -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            CommonInputType.EMAIL -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            CommonInputType.MULTI_LINE -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
            CommonInputType.PHONE -> InputType.TYPE_CLASS_PHONE
            CommonInputType.NUMBER -> InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            else -> InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        }

        if (inputType == CommonInputType.NONE) {
            viewBinding.editableTextInputInfo.isFocusable = false
            viewBinding.editableTextInputInfo.isClickable = true
        }

        viewBinding.editableTextInputInfo.typeface = Typeface.DEFAULT

    }

    fun clickListener(listener: (View) -> Unit) {
        viewBinding.editableTextInputInfo.setOnClickListener(listener)
    }

    private fun applyAttributes(attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = getStyleTypeArray(attrs, R.styleable.EditableTextView)
            val inputType = typedArray.getInt(
                R.styleable.EditableTextView_input_type, CommonInputType.TEXT
            )
            val imeOption =
                typedArray.getInt(R.styleable.EditableTextView_input_ime, CommonInputIme.DONE)
            val header = typedArray.getString(R.styleable.EditableTextView_input_header)

            typedArray.recycle()

            setHeader(header)
            setInputType(inputType)
            setImeOptions(imeOption)
        }
    }

    private fun setHeader(header: String?) {
        viewBinding.editableTextLabelLabel.text = header
    }

    private fun setImeOptions(imeOption: Int) {
        val ime = when (imeOption) {
            CommonInputIme.NEXT -> EditorInfo.IME_ACTION_NEXT
            CommonInputIme.DONE -> EditorInfo.IME_ACTION_DONE
            else -> EditorInfo.IME_ACTION_DONE
        }

        viewBinding.editableTextInputInfo.imeOptions = ime
    }

    private fun initWatcher() {
        viewBinding.editableTextInputInfo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // no-op
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                listener.onTextChanged(text?.toString() ?: "")
            }

            override fun afterTextChanged(text: Editable?) {
                // no-op
            }
        })
    }

}