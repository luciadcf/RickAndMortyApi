package es.luciadcf.rickandmorty.extension

import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import es.luciadcf.rickandmorty.R

fun Fragment.popBack() {
    getFocusedView().hideKeyboard()
    findNavController().navigateUp()
}

fun Fragment.getFocusedView(): View {
    return activity?.currentFocus ?: view?.rootView ?: View(activity)
}

fun Fragment.showErrorDialog(
    errorMessage: String = getString(R.string.unknown_error),
    dialogOnClickListener: DialogInterface.OnClickListener? = null
) {
    context?.let { con ->
        val dialog = AlertDialog.Builder(con)
            .setCancelable(true)
            .setMessage(errorMessage)
            .setTitle(null)
            .setPositiveButton(
                R.string.accept,
                dialogOnClickListener
                    ?: DialogInterface.OnClickListener { dialogInterface, _ -> dialogInterface.dismiss() })
            .create()
        dialog.setOnShowListener {
            dialog.getButton(DialogInterface.BUTTON_POSITIVE)
                .setTextColor(ContextCompat.getColor(con, R.color.black))
        }
        dialog.show()
    }
}